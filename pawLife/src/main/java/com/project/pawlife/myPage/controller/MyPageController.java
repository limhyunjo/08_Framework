package com.project.pawlife.myPage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

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


   // myPage에서 내가 쓴 후기 게시글 전체 조회
   @ResponseBody
   @GetMapping("selectReview")
   public List<Review> selectReview(
		@SessionAttribute("loginMember")Member loginMember){

	   int memberNo = loginMember.getMemberNo();
	   
	List<Review> reviewList = service.selectReview(memberNo);
	
	
	
	return reviewList;
   }

}
