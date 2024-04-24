package com.project.pawlife.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.member.model.service.MemberService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*@SessionAttributes ({ "key", "key", ...})
 * 
 * - Model에 추가된 속성( Attribute) 중
 * key 값이 일치하는 속성을 session scope로 변경
 * 
 * */
@SessionAttributes({"loginMember"})
@Controller
@RequiredArgsConstructor
@RequestMapping("member")
//@RequestMapping("member") <- 이게 있으면 모든 요청이 앞에 member가 있어야 함 안그럼 에러 남
@Slf4j
public class MemberController {

	private final MemberService service;


	 
	 /** 로그인 페이지 이동
		 * @return
		 */
		@GetMapping("login")
		public String loginPage() {
			return"member/login";
		}
		
		
		/** 회원 가입 페이지 이동
		 * @return
		 */
		@GetMapping("signup")
		public String signupPage() {
			
			return "member/signup";
		}
		
		
		
		/** 로그인 
		 * @param inputMember (@ModelAttribute 생략된 상태)
		 *                            (memberEmail, memberPw 세팅된 상태)
		 * @param ra : 리다이렉트 시 1회성으로 
		 *  request scope( 요청 받은 곳이랑 응답하는 화면 에서만 쓸 수 있는 것)
		 *  - 리다이렉트된 곳의 응답 화면
		 *                      로 데이터 전달해줌
		 *                      
		 *  @param model(  스프링의 데이터 전달용 객체 ) / request scope
		 *  
		 *                       
		 * @return
		 */
		@PostMapping("login")
		public String login( Member inputMember,
				RedirectAttributes ra,
				Model model // spring frame work로 써야 addAttribute 사용 가능
				) {
			
			// 로그인 서비스 호출 loginMember가 반환되어 올 것임
			Member loginMember = service.login(inputMember);
			
			
			// 로그인 실패 시
			if(loginMember == null) {
				ra.addFlashAttribute("message","아이디 또는 비밀번호가 일치하지 않습니다");
				
			}
			
			// 로그인 성공 시
			if(loginMember != null) {
				ra.addFlashAttribute("message","로그인 성공");
				// Session scope에 loginMember를 추가 
				// 세션 범위의 객체에 login 정보를 필요한 곳에 쓸 수 있게 올려 두겠다
				// 세션으로 올리기 -> Model
				
				model.addAttribute("loginMember", loginMember);
				
				// 1. 단계 : request scope에 세팅됨
				// 2. 단걔 : 클래스 위의 @SessionAttributes() 어노테이션 때문에
				// session scope로 이동됨
			}
		 return "redirect:/member/login";
		}
}
