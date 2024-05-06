package com.project.pawlife.review.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.review.model.dto.Review;
import com.project.pawlife.review.model.service.ReviewService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("review")
public class ReviewController {

	@Autowired
	private final ReviewService service;
	

	
	// 리뷰 게시판 리스트로 이동
	@GetMapping("reviewList")
	public String reviewPage(@RequestParam(value="cp",required=false, defaultValue="1") int cp, Model model, @RequestParam Map<String, Object> paramMap) { 
		
		Map<String, Object> map = null;
		
		
		if(paramMap.get("key") == null) { // 검색이 아닌 경우
			map = service.selectReviewList(cp);
			
		} else {
			map = service.selectReviewList(paramMap, cp);
		}

		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("reviewList", map.get("reviewList"));
		
		return "review/reviewList";
	}
	

	// 후기 상세 페이지 이동
	@GetMapping("reviewDetail")
	public String reviewDetail() {
		return "review/reviewDetail";
	}
	
	
	/** 리뷰 게시글 상세 조회
	 * @return
	 * @throws ParseException 
	 */
	@GetMapping("reviewList/{reviewNo:[0-9]+}")
	public String reviewDetail(@PathVariable("reviewNo") int reviewNo, @SessionAttribute(value="loginMember", required = false) Member loginMember, 
			Model model, RedirectAttributes ra, HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("reviewNo",reviewNo);
		
		Review review = service.selectOneReview(map);
		
		String path = null;
		
		if(review == null) {
			
			path = "redirect:/review/reviewList";
			ra.addFlashAttribute("message","게시글이 존재하지 않습니다.");
		}
		else {

			/* 조회수 증가 */
			if(loginMember == null || loginMember.getMemberNo() != review.getMemberNo()) {
				
				Cookie[] cookies = req.getCookies();
				
				Cookie c = null;
				if(cookies!=null ) {
					for(Cookie temp : cookies) {
						if(temp.getName().equals("readReviewNo")) {
							c=temp;
							break;
						}
					}
				}
				
				int result = 0;
				
				if(c==null) {
					c=new Cookie("readReviewNo","["+reviewNo+"]");
					result = service.updateReadCount(reviewNo);
				}else {
					
					if(c.getValue().indexOf("["+reviewNo+"]")== -1) {
						
						c.setValue(c.getValue()+"["+reviewNo+"]");
						result = service.updateReadCount(reviewNo);
						
					}
					
				}
				
				
				if(result>0) {
					
					review.setReadCount(result);
					
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
			
			path = "review/reviewDetail";
			model.addAttribute("review", review);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		return path;
	}
	

	// 후기 글쓰기화면으로 이동
	@GetMapping("reviewWrite")
	public String reviewWrite() { return "review/reviewWrite"; }
	
	
	/** 후기 글 작성 (로그인 세션 등록시 회원번호 추가해서 insert 진행 수정)
	 * @param map
	 * @return
	 */
	@PostMapping("reviewInsert")
	public String reviewInsert(Review inputReview, @RequestParam("thumnailImg") MultipartFile thumnailImg,
			@SessionAttribute("loginMember") Member loginMember, Model model) {

		int memberNo = loginMember.getMemberNo();
		
		int result = service.reviewInsert(inputReview, thumnailImg, memberNo);
		
		String path = "";
		String message = "";
		
		if(result > 0) {
			path = "reviewList"; // reviewDetail 구현 전까지 임시로 포워드하는 경로
			message = "후기글 등록이 완료되었습니다.";
			
		} else {
			path = "reviewWrite";
			message = "후기글 등록이 실패되었습니다.";
		}
		
		model.addAttribute("message", message);
		
		return "redirect:/review/" + path;
	}

	
	
	/** 후기 수정 페이지 이동
	 * @return
	 */
	@GetMapping("editReview/{reviewNo:[0-9]+}/update")
	public String reviewUpdate(
			@PathVariable("reviewNo") int reviewNo, @SessionAttribute("loginMember") Member loginMember, 
			RedirectAttributes ra, Model model
			) {
		
		// 수정에 필요한 값
		Map<String, Integer> map = new HashMap<>();
		map.put("reviewNo",reviewNo);
		
		Review review = service.selectOneReview(map);
		
		String message = null;
		String path = null;
		
		if(review == null) {
			message = "해당 게시글이 존재하지 않습니다.";
			path = "redirect:/review/reviewList";
			
			ra.addFlashAttribute("message",message);
			
		} else if(review.getMemberNo() != loginMember.getMemberNo()) {
			message = "자신이 작성한 글만 수정할 수 있습니다.";
			// 해당 글 상세 조회
			path = "redirect:/review/reviewList";
			path = String.format("redirect:/review/reviewList/%d",reviewNo);
			
			ra.addFlashAttribute("message",message);
			
		} else { // 업데이트 가능한 경우
			path ="review/reviewUpdate";

			model.addAttribute("review",review);
		}
		return path;
	}
	
	/** 후기 게시글 수정
	 * @param reviewNo
	 * @param inputReview
	 * @param loginMember
	 * @param thumnailImg
	 * @param deleteOrder
	 * @param querystring
	 * @param statusCheck
	 * @param ra
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@PostMapping("editReview/{reviewNo:[0-9]+}/update")
	public String editReview(@PathVariable("reviewNo") int reviewNo, Review inputReview, @SessionAttribute("loginMember") Member loginMember,
			@RequestParam("thumnailImg") MultipartFile thumnailImg, @RequestParam(value="deleteOrder", required=false)String deleteOrder,
			@RequestParam(value="querystring", required=false, defaultValue="")String querystring, @RequestParam("statusCheck") int statusCheck,
			RedirectAttributes ra)  throws IllegalStateException, IOException{
		
		int memberNo = loginMember.getMemberNo();
		inputReview.setMemberNo(memberNo);
		
		int result = service.reviewUpdate(inputReview, thumnailImg, statusCheck); 
		
		String message = "";
		String path = "";
		
		if(result > 0) {
			message = "게시글이 수정되었습니다.";
			path = String.format("/review/reviewList/%d%s", reviewNo, querystring);
		} else {
			message = "수정 실패";
			path = "update"; // 수정화면 전환 상대 경로
		}
		
		ra.addFlashAttribute("message",message);
		
		return  "redirect:" + path;
	}
	
	/** 게시글 삭제
	 * @param loginMember
	 * @param reviewNo
	 * @param ra
	 * @return
	 */
	@PostMapping("editReview/{reviewNo:[0-9]+}/delete")
	public String deleteReview(
			@SessionAttribute("loginMember") Member loginMember, @PathVariable("reviewNo") int reviewNo, RedirectAttributes ra) {
		
		int memberNo = loginMember.getMemberNo();
		
		Map<String, Integer> map = new HashMap<>();
		map.put("memberNo", memberNo);
		map.put("reviewNo", reviewNo);
		
		int result = service.reviewDelete(map);
		
		String path = null;
		String message = null;
		
		if(result>0) {
			path = "review/reviewList";
			message="삭제 되었습니다.";
		}else {
			path = "review/reviewList/" + reviewNo;
			message="삭제 실패.";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:/" + path;
	}
	
	@GetMapping("path")
	public String getMethodName(@RequestParam String param) {
		return new String();
	}

}