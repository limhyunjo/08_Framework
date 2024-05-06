package com.project.pawlife.adoption.controller;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.adoption.model.service.AdoptionService;
import com.project.pawlife.member.model.dto.Member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("adoption")
@Slf4j
public class AdoptionController {
	
	@Autowired
	private final AdoptionService service;
	
	/** 입양 게시판 리스트 항목으로 이동 + 게시글 검색
	 * @param cp : 현재 조회 요청한 페이지(없으면 1)
	 * @return
	 */
	@GetMapping("adoptionList")
	public String adoptionPage(
			@RequestParam(value="cp",required=false, defaultValue="1") int cp /*페이지 수*/,
			Model model,
			@RequestParam Map<String, Object> paramMap
			) {
		
		// 조회 서비스 요청 후 결과 반환
		Map<String, Object> map = null;
		
		if(paramMap.get("key")==null) {
		
			map = service.selectAdoptList(cp);
		
		}else {
			
			// 검색 서비스 호출
			map = service.searchList(paramMap, cp);
			
		}
		
		model.addAttribute("pagination",map.get("pagination"));
		model.addAttribute("adoptList",map.get("adoptList"));
		
		
		return "adoption/adoptionList";
	}
	
	/** 입양 상세 조회
	 * @return
	 * @throws ParseException 
	 */
	@GetMapping("adoptionList/{adoptNo:[0-9]+}")
	public String adoptionDetail(
			@PathVariable("adoptNo") int adoptNo,
			Model model,
			RedirectAttributes ra,
			@SessionAttribute(value="loginMember", required = false) Member loginMember,
			HttpServletRequest req,  // 요청에 담긴 쿠키 얻어오기
			HttpServletResponse resp // 새로운 쿠키를 만들어 응답하기
			) throws ParseException {
		
		Map<String, Integer> map = new HashMap<>();
		if(loginMember!=null) {
			int memberNo = loginMember.getMemberNo();
			map.put("memberNo",memberNo);
		}
		
		map.put("adoptNo",adoptNo);
		
		
		Adopt adopt = service.selectOneAdopt(map);
		
		String path = null;
		
		if(adopt == null) {
			
			path = "redirect:/adoption/adoptionList";
			ra.addFlashAttribute("message","게시글이 존재하지 않습니다.");
			
		}
		else {
			
			
			/* 쿠키를 이용한 조회수 증가 */
			
			if(loginMember == null || loginMember.getMemberNo() != adopt.getMemberNo()) {
				
				
				Cookie[] cookies = req.getCookies();
				
				
				Cookie c = null;
				if(cookies!=null ) {
					for(Cookie temp : cookies) {
						if(temp.getName().equals("readAdoptNo")) {
							c=temp;
							break;
						}
					}
				}
				
				int result = 0;
				
				if(c==null) {
					c=new Cookie("readAdoptNo","["+adoptNo+"]");
					result = service.updateReadCount(adoptNo);
				}else {
					
					if(c.getValue().indexOf("["+adoptNo+"]")== -1) {
						
						c.setValue(c.getValue()+"["+adoptNo+"]");
						result = service.updateReadCount(adoptNo);
						
					}
					
				}
				
				
				if(result>0) {
					
					adopt.setReadCount(result);
					
					c.setPath("/");
					
					// 수명 지정
					Calendar cal = Calendar.getInstance(); // 싱글톤 패턴
					cal.add(cal.DATE, 1);

					// 날짜 표기법 변경 객체 (DB의 TO_CHAR()와 비슷)
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					// java.util.Date
					Date a = new Date(); // 현재 시간

					Date temp = new Date(cal.getTimeInMillis()); // 다음날 (24시간 후)
					// 2024-04-16 12:30:10

					Date b = sdf.parse(sdf.format(temp)); // 다음날 0시 0분 0초

					// 다음날 0시 0분 0초 - 현재 시간
					long diff = (b.getTime() - a.getTime()) / 1000;
					// -> 다음날 0시 0분 0초까지 남은 시간을 초단위로 반환

					log.debug("diff {}", diff);
					
					c.setMaxAge((int) diff); // 수명 설정

					resp.addCookie(c); // 응답 객체를 이용해서 클라이언트에게 전달
					
				}
				
				
			}
			
			
			path = "adoption/adoptionDetail";
			model.addAttribute("adopt",adopt);
			
			
			
		}
		
		return path;
	}
	
	
	
	/** 입양 글쓰기 화면 이동
	 * @return
	 */
	@GetMapping("adoptionWrite")
	public String adoptionWrite() {
		
		
		
		return "adoption/adoptionWrite";
	}
	
	
	
	/** 입양 게시글 작성
	 * @param inputAdopt
	 * @param thumnailImg
	 * @return
	 */
	@PostMapping("adoptionInsert")
	public String adoptionInsert(
			Adopt inputAdopt,
			@RequestParam("thumnailImg") MultipartFile thumnailImg,
			@RequestParam("adoptContent") String adoptContent,
			@SessionAttribute(value="loginMember", required = false) Member loginMember
			) {


		
		int memberNo = loginMember.getMemberNo(); 
		int result = service.adoptionInsert(inputAdopt,thumnailImg,memberNo);
		
		
		String path=null;
		
		
		if(result>0) {
			
			path="/adoption/adoptionList";
			
		}else {
			path="/adoption/adoptionWrite";
		}
		
		return "redirect:"+path;
	}
	
	
	
	/** 입양문의
	 * @return
	 */
	@GetMapping("adoptionContact")
	public String contact() { return "adoption/adoptionContact"; }
	
	
	
	
	/** 입양 수정 화면 전환
	 * @return
	 */
	@GetMapping("editAdoption/{adoptNo:[0-9]+}/update")
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
			path = "redirect:/adoption/adoptionList"; // 메인페이지로 리다이렉트 
			
			ra.addFlashAttribute("message",message);
			
		}else if(adopt.getMemberNo() != loginMember.getMemberNo()) {
			message = "자신이 작성한 글만 수정할 수 있습니다.";
			// 해당 글 상세 조회
			path = String.format("redirect:/adoption/adoptionList/%d",adoptNo);
			
			ra.addFlashAttribute("message",message);
			
		}else {
			path ="adoption/adoptionUpdate";
			
			// forward의 경우
			model.addAttribute("adopt",adopt);
		}
		
		return path;
	}
	
	/** 수정하기
	 * @param adoptNo
	 * @param adoptInput
	 * @param loginMember
	 * @param thumnailImg
	 * @param deleteOrder
	 * @param querystring
	 * @param ra
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@PostMapping("editAdoption/{adoptNo:[0-9]+}/update")
	public String editAdopt(
			@PathVariable("adoptNo") int adoptNo,
			Adopt adoptInput,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("thumnailImg") MultipartFile thumnailImg,
			@RequestParam(value="deleteOrder", required=false)String deleteOrder,
			@RequestParam(value="querystring", required=false, defaultValue="")String querystring,
			@RequestParam("statusCheck") int statusCheck,
			RedirectAttributes ra
			) throws IllegalStateException, IOException{
		
		
		int memberNo = loginMember.getMemberNo();
		adoptInput.setMemberNo(memberNo);
		
		int result = service.adoptUpdate(adoptInput,thumnailImg,statusCheck);
		
		String message = null;
		String path = null;
		
		if(result>0) {
			message = "게시글이 수정되었습니다.";
			path = String.format("/adoption/adoptionList/%d%s", adoptNo, querystring);
		}else {
			message = "수정 실패";
			path = "update"; // 수정화면 전환 상대 경로
		}
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:"+path;
		
	}
	
	
	/** 게시글 삭제
	 * @param loginMember
	 * @param adoptNo
	 * @param ra
	 * @return
	 */
	@PostMapping("editAdoption/{adoptNo:[0-9]+}/delete")
	public String deleteAdopt(
			@SessionAttribute("loginMember") Member loginMember,
			@PathVariable("adoptNo") int adoptNo,
			RedirectAttributes ra
			) {
		
		int memberNo = loginMember.getMemberNo();
		
		Map<String, Integer> map = new HashMap<>();
		map.put("memberNo", memberNo);
		map.put("adoptNo", adoptNo);
		
		int result = service.adoptDelete(map);
		
		String path = null;
		String message = null;
		
		if(result>0) {
			path = "/adoption/adoptionList";
			message="삭제 되었습니다.";
		}else {
			path = "/adoption/adoptionList/"+adoptNo;
			message="삭제 실패.";
		}
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:"+path;
	}
	
	
	
	
	/** 북마크
	 * @param map
	 * @return 
	 */
	@ResponseBody
	@PostMapping("bookmark")
	public int bookMark(
			 @RequestBody Map<String,Integer> map 
			) {
		
		
		
		 return service.bookCheck(map);
	}
	
	
	
	
	/** 문의 페이지 이동
	 * @return
	 */
	@GetMapping("contactAdopt/{adoptNo:[0-9]+}")
	public String contactAdopt(@PathVariable("adoptNo") int adoptNo) {
		return "adoption/adoptionContact";
	}
	
	
	
	
	/** 문의 내용 이메일 전송
	 * @param aoptNo
	 * @param loginMember
	 * @param ra
	 * @return
	 */
	@PostMapping("contactAdopt/{adoptNo:[0-9]+}/contact")
	public String contactMail(
			@PathVariable("adoptNo") int adoptNo,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra,
			Adopt contactInput,
			Model model
			) {
		
		String email = service.writerEmail(adoptNo);
		
		String adoptName = service.adoptName(adoptNo);
		
		
		int memberNo=loginMember.getMemberNo();
		
		String toEmail = service.toEmail(memberNo);
		contactInput.setMemberEmail(toEmail);
		
		contactInput.setAdoptName(adoptName);
		
		contactInput.setMemberNo(memberNo);
		
		contactInput.setContactEmail(email);
		
		contactInput.setAdoptNo(adoptNo);
		
		
		
		int result = service.sendEmail("adoptionContactMail",contactInput);
		
		model.addAttribute(contactInput);
		
		String path = null;
		String message = null;
		
		if(result>0) {
			path = "/adoption/adoptionList/"+adoptNo;
			message="문의가 발송되었습니다.";
			
		}else {
			
			path = "/adoption/contactAdopt/"+adoptNo;
			message="문의 발송 실패.";
			
		}
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:"+path;
	}
	
	

}
