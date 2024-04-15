package com.kh.test.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.test.user.model.dto.User;
import com.kh.test.user.model.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor // 이거 쓸 때는 꼭 final이 있어야 함
public class UserController {


	 private final UserService service;
	
	
	@RequestMapping("/") // "/" 요청 매핑 (method 가리지 않음)
	public String mainPage() {
		
		
		return "static/index"; 
	}
	
	//Model 값을 request scope으로 전달하는 객체 (요청이 유지되는 동안은 쓸 수 있다 
	// forward 요청 위임으로 대신 해주는 동안 사용 가능
	@GetMapping("selectName")            // input의 name 속성값 받아옴
	public String userSearch(@RequestParam ("inputName")String inputName, Model model) {
		
		// 회원 1명 검색하는 경우 - user dto 하나로 조회 결과 저장 가능
		// 이름으로 검색하는 경우 - 회원 n명 검색 / user가 여러명 필요
		
		// -> List<User> 형태의 목록으로 모든 조회 결과를 저장
		//  많이 조회하는 경우 list 사용
		
		// 이름으로 조회 -> 결과 행 여러개 가능성 O
		// -> List로 결과 반환 받기
		
	// User 1행 조회 시 
		// 결과 없음 -> null인지 확인
		
		// User N행 조회 (list 조회)
		// 결과 없음 -> List.isEmpty( ) ==true 인지 확인
		// (List는 조회 결과가 없어도 List 객체는 무조건 생성됨 !!)
		// -> null 아님
		
		
		
		List<User> userList = service.selectName(inputName);
		
		String path = null;
		
		// templates/todo/detail.html
		if(userList.isEmpty()) { //조회 결과 있을 경우
			path ="searchFail"; // forward 경로
			
			// request scope 값 세팅
			model.addAttribute("userList",userList) ;
			
		}else {
			path = "searchSuccess";  // 메인 페이지로 재요청
			
	
			// 이름으로 검색하는 경우 겹치는 이름이 있을 수 있어 list로 조회
			
			
		}
		
		return path;
	}


}
