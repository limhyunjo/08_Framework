package edu.kh.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.main.model.service.MainService;
import lombok.RequiredArgsConstructor;


@Controller // bean에 객체  등록 spring 이 생성 등록 관리해줌
// 컨트롤러로 등록이 되면 Http 요청을 처리하고 응답을 반환할 수 있다
@RequiredArgsConstructor
public class MainController {

	
	private final MainService service;
	
	@RequestMapping("/") // "/" 요청 매핑 (method 가리지 않음)
	public String mainPage(
			 ) {
		
		
		return "common/main"; // common의 main 파일로 forward한다
	}
	
	
	
	/** 비밀번호 초기화
	 * @param inputNo : 초기화할 회원 번호
	 * @return result
	 */
	@ResponseBody
	@PutMapping("resetPw")
	public int resetPw(@RequestBody int inputNo) {
		
		return service.resetPw(inputNo);
	}
	
	
	/** 특정 회원 탈퇴 복구
	 * @param inputNo
	 * @return result
	 */
	@ResponseBody 
	// 메서드의 반환값을 Http 응답 본문으로 사용 , return의 값을 응답으로 보낼 수 있게 해줌
	@PutMapping("resetSecession") 
	public int resetSecession(@RequestBody int inputNo) {
		// @RequestBody : 요청 본문(body) 의 데이터를 메서드 매개 변수로 바인딩한다 
		
		return service.resetSecession(inputNo);
	}
	
	
	// LoginFilter -> loginError 리다이렉트
	// -> message를 만들어서 메인 페이지로 리다이렉트
	
	@GetMapping("loginError")
	public String loginError( RedirectAttributes ra) {
		ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
		
		return "redirect:/";
	}
	
	
	
	
}
