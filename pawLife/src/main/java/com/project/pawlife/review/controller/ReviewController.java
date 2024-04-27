package com.project.pawlife.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.pawlife.review.model.dto.Review;
import com.project.pawlife.review.model.service.ReviewService;

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
	public String reviewPage() { return "review/reviewList"; }
	
	
	// 후기 글쓰기화면으로 이동
	@GetMapping("reviewWrite")
	public String reviewWrite() { return "review/reviewWrite"; }
	
	
	/** 후기 글 작성 (로그인 세션 등록시 회원번호 추가해서 insert 진행 수정)
	 * @param map
	 * @return
	 */
	@PostMapping("reviewWrite")
	public String reviewWrite(Review inputReivew, Model model) {

		// 추가할것) boardCode, 로그인한 회원 번호 inputReivew에 세팅 <- pathVariable, sessionattribute

		int result = service.reviewWrite(inputReivew);
		
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
		
		return "redirect:" + path;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 후기 상세 페이지
	 * @return
	 */
	@GetMapping("reviewDetail")
	public String reviewDetail() {
		return "review/reviewDetail";
	}
	
	/** 후기 수정 페이지
	 * @return
	 */
	@GetMapping("reviewUpdate")
	public String reviewUpdate() {
		return "review/reviewUpdate";
	}

	
}
