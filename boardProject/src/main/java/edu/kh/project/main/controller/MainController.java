package edu.kh.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // bean에 객체  등록 spring 이 생성 등록 관리해줌
public class MainController {

	@RequestMapping("/") // "/" 요청 매핑 (method 가리지 않음)
	public String mainPage() {
		
		
		return "common/main"; // common의 main 파일로 forward한다
	}
	
	
}
