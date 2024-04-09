package com.kh.test.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.test.user.model.dto.User;
import com.kh.test.user.model.service.UserService;


import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserController {

	@Autowired
	 private UserService service;
	
	
	@RequestMapping("/") // "/" 요청 매핑 (method 가리지 않음)
	public String mainPage() {
		
		
		return "static/index"; 
	}
	
	@GetMapping("searchSuccess")            // input의 name 속성값 받아옴
	public String userSearch(@RequestParam ("userId")String userId, Model model) {
		
		User user = service.userSearch(userId);
		
		String path = null;
		
		// templates/todo/detail.html
		if(user !=null) { //조회 결과 있을 경우
			path ="searchSuccess"; // forward 경로
			
			// request scope 값 세팅
			model.addAttribute("user",user) ;
			
		}else {
			path = "searchFail";  // 메인 페이지로 재요청
			
	
		}
		
		return path;
	}


}
