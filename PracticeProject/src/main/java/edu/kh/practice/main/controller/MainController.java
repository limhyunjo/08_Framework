package edu.kh.practice.main.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 요청 / 응답을 제어할 컨트롤러 역할 명시 
// 컴파일러에게 컨트롤러 역할을 할 것이라고 알려줌 + Bean 으로 등록( == 객체로 생성해서 스프링이 관리)
// 실행이 되면 객체로 동작

public class MainController {

	// @RequestMapping("요청 주소")
	// - 요청 주소를 처리할 메서드를 매핑하는 어노테이션
	
	//1) 메서드에 작성 : 
	// - 요청 주소와 메서드를 매핑
	// - Get/Post 가리지 않고 매핑 (속성을 통해서 지정 가능 
	// -> @RequestMapping(value="test", method=RequestMethod.GET) - 요즘은 잘 안씀
	
	//2) 클래스에 작성
	// spring boot controller에서 특수한 경우를 제외하고는 매핑 주소 제일 앞에 "/"를 작성하지 않는다
	// - 공통 주소를 매핑
	// ex) /todo/insert, /todo/select, /todo/update
	
	// 클래스 위에 @RequestMapping("todo")
	
	// 아래 메서드에는  @RequestMapping("insert")라고만 쓰면 됨
	
	
	
	@RequestMapping("/") // "/" 요청 매핑 (method 가리지 않음)
	public String mainMethod() {
	// 메서드 만들 때 항상 public String으로 함
		
		
		System.out.println("/main 요청 받음");
		
		return "common/main"; // common의 main 파일로 forward한다
	}
	
	
	@RequestMapping("test")
	public String testMethod() {
		
		System.out.println("/test 요청");
		
		
		/*
		 * Controller 메서드의 반환형이 String인 이유 !!
		 * 
		 * - 메서드에서 반환되는 문자열이 forward할 html 파일의 경로가 되기 때문!!!
		 * 
		 * */
		
		// src/main/resources/templates/test.html 가 세부 경로임
		
		/*Thymeleaf :JSP 대신 사용하는 템플릿 엔진
		 * 
		 * classpath == src/main/resources  (java 프로젝트에 지정이 되어있음)
		 * 접두사 : classpath:/templates/
		 * 접미사 : .html 
		 * 
		 * */
		
		return "test"; // 접두사 + 반환값 + 접미사 경로의 html로 forward
		
		/* 접두사, 접미사, forward 설정은 View resolver(해결사) 
		 * 
		 *  Controller가 "test"라는 문자열을 반환해줌
		 *  View Resolver가 접두사, 접미사를 준비하고 있다가 가운데 반환받은 주소로 forward할 준비를 해줌
		 *  
		 *  == forward, 접두사, 접미사 설정은 View Resolver 객체가 담당
		 * */
	}
	
}