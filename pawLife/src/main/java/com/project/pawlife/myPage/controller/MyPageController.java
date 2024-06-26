package com.project.pawlife.myPage.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.myPage.model.service.MyPageService;
import com.project.pawlife.review.model.dto.Review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

	
	private final MyPageService service;


	//  myPage 화면 
	@GetMapping("first")
	public String first() {
		return "myPage/myPage-first";
		
		
	}
	
	
	// myPage 수정 화면으로 보내줌
   @GetMapping("myPage-profileupdate")
   public String profileUpdate() {
      
      return "myPage/myPage-profileupdate";
   }
   
  // myPage 수정 화면에서 프로필로 보내줌
   @GetMapping("myPage-first")
   public String profileUpdateComplete() {
      
      return "myPage/myPage-first";
   }
   
   
   
   
   //로그인한 회원이 올린 입양 게시글 전체 조회
   @ResponseBody
   @GetMapping("selectAdoptList")
   public  Map<String, Object> selectAdopt(
		   @SessionAttribute("loginMember")Member loginMember,
		  @RequestParam(value="cp", required=false, defaultValue = "1") int cp ){
	   
	   
	   int memberNo = loginMember.getMemberNo();
	   
	   Map<String, Object> map = service.selectAdopt(memberNo,cp);
	
	
	   
	   return map;
   }


   // 로그인한 회원이 작성한 후기 게시글 전체 조회
   @ResponseBody
   @GetMapping("selectReview")
   public Map<String, Object> selectReview(
		@SessionAttribute("loginMember")Member loginMember,
		@RequestParam(value="cp", required=false, defaultValue = "1") int cp 
		   ){

	   int memberNo = loginMember.getMemberNo();
	   
	   Map<String, Object> map = service.selectReview(memberNo,cp);
	
	
	
	return map;
   }
   
	   /** 로그인한 회원이 작성한 댓글 전체 조회
	 * @param loginMember
	 * @param ra
	 * @return
	 */
    @ResponseBody
	@GetMapping("selectComment")
	public Map<String, Object> selectComment(@SessionAttribute("loginMember") Member loginMember,
			@RequestParam(value="cp", required=false, defaultValue = "1") int cp,
			RedirectAttributes ra) {

		int memberNo = loginMember.getMemberNo();

		Map<String, Object> map = service.selectComment(memberNo,cp);

		return map;
	}

   
    /** 로그인한 회원이 북마크한 게시물 조회
     * @param loginMember
     * @param ra
     * @return bookmarkList
     */
    @ResponseBody
    @GetMapping("selectBookMark")
    public Map<String, Object> selectBookMark(
    	@SessionAttribute("loginMember") Member loginMember, 
    	@RequestParam(value="cp", required=false, defaultValue = "1") int cp,
    	RedirectAttributes ra
    		){
    	int memberNo = loginMember.getMemberNo();

    	Map<String, Object> map = service.selectBookMark(memberNo,cp);
    	
    	
    	return map;
    }
    
    
   /** 개인정보 수정
 * @param inputMember
 * @param loginMember
 * @param ra
 * @return
 */
@PostMapping("profileUpdate")
   public String profileUpdate(
		   Member inputMember,
		   @SessionAttribute("loginMember") Member loginMember,
		   RedirectAttributes ra
		   ) {
      
	   // inputMember에 로그인한 회원 번호 추가
	   int memberNo = loginMember.getMemberNo();
	   inputMember.setMemberNo(memberNo);
	   
	   // 회원 정보 수정 서비스
	   int result = service.profileUpdate(inputMember);
	   
	   String message= null;
	   
	   if(result>0) {
		   message="회원 정보가 수정되었습니다";
		   
		   // loginMember에 저장된 정보 입력한 정보로 수정
		   loginMember.setMemberNickname(inputMember.getMemberNickname());
			loginMember.setMemberTel(inputMember.getMemberTel());
	   } else {
		   message="회원 정보가 수정되지 않았습니다";
	   }
       
	   ra.addFlashAttribute("message",message);
	   
       return "redirect:/myPage/myPage-profileupdate";
   }
   
   /** 비밀번호 수정
 * @param loginMember
 * @param currentPw
 * @param newPw
 * @param ra
 * @return
 */
@PostMapping("changePw")
   public String changeMemberPw(
		   @SessionAttribute("loginMember") Member loginMember,
		   @RequestParam("currentPw") String currentPw,
		   @RequestParam("newPw") String newPw,
		   RedirectAttributes ra
		   ) {
	   
	   
	   int memberNo = loginMember.getMemberNo();
	   
	   int result = service.changeMemberPw(memberNo, currentPw, newPw);
	   
	   String path = null;
	   String message = null;
	   
		if(result>0) {
			message=("비밀번호가 변경 되었습니다.");
		
			path="/myPage/myPage-profileupdate";
		}else {
		 message = "현재 비밀번호가 일치하지 않습니다.";

		 path= "/myPage/myPage-profileupdate";
		}
		
		 ra.addFlashAttribute("message",message);
		 return "redirect:" +path;
   }


   /** 회원 탈퇴
 * @param loginMember
 * @param status
 * @param ra
 * @return
 */
@PostMapping("memberdel")
   public String memberDel(
		   @SessionAttribute("loginMember") Member loginMember,
		   SessionStatus status,			
			 RedirectAttributes ra
		   ) {
	   
	   int memberNo = loginMember.getMemberNo();
	   int result = service.deleteMember(memberNo);
	   
	   String path = null;
	   String message = null;
	   
	   if(result > 0) {
		   message = "탈퇴 되었습니다";
		   status.setComplete();
		   path="/";
	   }else {
		   message = "탈퇴 실패하였습니다";
		   path = "/myPage/myPage-first";
	   }
	   
	   ra.addFlashAttribute("message",message);
	   return "redirect:" + path;
   }
   
   
   /**
	 * 프로필 이미지 변경
	 * 
	 * @param profileImg
	 * @param loginMember
	 * @param ra
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
@PostMapping("profile")
public String profile(
		@RequestParam("imageInput") MultipartFile imageInput,
		@SessionAttribute("loginMember") Member loginMember, 
		@RequestParam("statusCheck") int statusCheck,
		// session에 있는 loginMember의 주소를 가져옴
		RedirectAttributes ra) throws IllegalStateException, IOException {
	
	// 로그인한 회원 번호
	int memberNo = loginMember.getMemberNo();
	
	// 서비스 호출
	// -> /myPage/profile/변경된 파일명 형태의 문자열
	//  현재 로그인한 회원의 PROFILE_IMG 컬럼 값으로 수정( UPDATE )
	
	int result = service.profile(imageInput, loginMember,statusCheck);
	
	
	String message = null;
	
	if(result >0) {
		message = "변경 성공";
		
		//세션에 저장된 로그인 회원 정보에서
		// 프로필 이미지 수정
		
	} else {
		message = "변경 실패";
	}
	
	ra.addFlashAttribute("message",message);
	
	
	
	return "redirect:/myPage/first"; 
 }

 //----------------------------------------------------------------------

   // 입양 리스트 
   

	/** 입양리스트에서 수정 버튼 클릭시 
	 *  해당 게시글의 입양 수정 화면으로 전환
	 * @return
	 */
	@GetMapping("adoption/editAdoption/{adoptNo:[0-9]+}/update")
	public String adoptionUpdate(
			@PathVariable("adoptNo") int adoptNo,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra,
			Model model
			) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("adoptNo",adoptNo);
		Adopt adopt = service.selectOneAdopt(map);
		
		
		String message = null;
		String path = null;
		
		if(adopt == null) {
			message = "해당 게시글이 존재하지 않습니다.";
			path = "redirect:/myPage/first"; 
			
			ra.addFlashAttribute("message",message);
			
	
		}else {
			path ="adoption/adoptionUpdate";
			
			// forward의 경우
			model.addAttribute("adopt",adopt);
		}
		
		return path;
	}
	


	/** 입양완료 여부 변경
	 * @param adopt
	 * @return result
	 */
	@ResponseBody
	@PutMapping("adoptDelComplete")
	public int changeAdoptComplete(@RequestBody Adopt adopt,
			 @SessionAttribute("loginMember") Member loginMember,		
			   RedirectAttributes ra) {
		
		   int memberNo = loginMember.getMemberNo();
		   int result = service.changeAdoptComplete(memberNo, adopt);
		   
		
		return result;
	}

}