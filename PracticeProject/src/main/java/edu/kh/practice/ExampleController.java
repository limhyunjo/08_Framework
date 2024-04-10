package edu.kh.practice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


// instance : 개발자가 만들고 관리하는 객체
// Bean : 스프링이 만들고 관리하는 객체
@Controller  // 요청/ 응답 제어 역할 명시 + Bean 등록
public class ExampleController {

	
	/*요청 주소 매핑하는 방법
	 * 
	 * 1) @RequestMapping("주소")
	 * 
	 * 2) @GetMapping("주소") : Get 방식 요청 매핑 // 조회 select
	 *     @PostMapping("주소") : POST 방식 요청 매핑 // 삽입 insert
	 *     
	 *     @PutMapping("주소") : PUT 방식 요청 // 수정 update 
	 *                                   (form, js, a태그 요청 불가 -> 비동기로만 할 수 있음) // 직접 쓰는 주소는 불가능
	 *     @DeleteMapping("주소") : DELETE 방식 요청 // 삭제 delete
	 *                                   (form, js, a태그 요청 불가 -> 비동기로만 할 수 있음)
	 * */
	
	@GetMapping("example") // / example Get 방식 요청 매핑
	public String exampleMethod() {
		
		// forward 하려는 html 파일 경로 작성
		// 단, ViewResolver가 제공하는
		// Thymeleaf 접두사 , 접미사 제외하고 작성
		
		return "example";
	}
	
}
