package edu.kh.practice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;


@Controller
@RequestMapping("main")
@Slf4j // lombok이라는 라이브러리가 제공해주는 log 를 이용해서 화면에 메시지 출력
public class ParameterController {

	
	
	@GetMapping("main") // /param/param Get방식 요청 매핑
	public String paramMain() {
	
		
		return "param/param-main";
	}
	
}
