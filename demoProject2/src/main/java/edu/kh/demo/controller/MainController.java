package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Controller : 제어기 / 요청 , 응답을 제어
// ->  요청에 따라 알맞은 서비스 호출할지 제어
// + 서비스 결과에 따라 어떤 응답을 할지 제어


@Controller // 요청 / 응답 제어 역할 명시  + Bean으로 등록 
public class MainController {
	
	// "/" 주소 요청 시 해당 메서드와 매핑
	// - 메인 페이지 지정 시에는  "/ " 작성 가능
	@RequestMapping("/")
	public String mainPage() {
		
		// forward : 요청 위임 
		
		// thymeleaf : spring boot 에서 사용하는 템플릿 엔진
		
		// thymeleaf를 이용한 html 파일로 forward 시
		// 사용되는 접두사 , 접미사가 존재 
		
		// 접두사 : classpath:/tempaltes/
		// 접미사 : .html 
		
		
		return "common/main"; 
	}

}
