package edu.kh.project.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;



/* @RestController   (REST API 구축을 위해 사용하는 컨트롤러) // 자원만 돌려줌
 * = @Controller ( 요청/응답 제어 + bean 등록)
 *   + @ResponseBody (응답 본문 으로 데이터를 반환 (fetch 구문으로 반환)
 *   
 *   -> 모든 응답을 응답 본문(ajax)으로 반환하는 컨트롤러
 *   
 *   // 이 컨트롤러 클래스는 모든 응답이 비동기임
 *    // 비동기는 @ResponseBody, @RequstBody 사용
 *    
 *   */
@RestController 
//@ResponseBody 이걸 써야 값이 그대로 돌아감
/// @ResponseBody 이거 한번에 쓰는 법
@Controller
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {

	private final CommentService service;
	
	//  fetch("/comment?boardNo=" + boadNo) 요청 
	// 위에서 "comment"가 있어서 아래는 빈칸으로 해도 됨
	
	// value 속성 : 매핑할 주소
	// produces 속성 : 응답할 데이터의 형식을 지정
	/** 
	 * @param boardNo
	 * @return
	 */
	@GetMapping(value="", produces = "application/json")
	public List<Comment> select(@RequestParam("boardNo") int boardNo){
		// json으로 돌려보내야 하는 상황에서는 다 이거 씀 (실무에서)
		
		// HttpMessageConverter가
		// List를  JSON (문자열)로 변환해서 응답
		
		return service.select(boardNo); // fetch로 돌려보내줌
		
	}
	
	
	
	
	/** 댓글 등록
	 * @return
	 */
	@PostMapping("")
	public int insert(@RequestBody Comment comment) {
		
		
		// 요청 데이터가 JSON으로 명시됨
		// headers : {"Content-Type" : "application/json"}
		
		// -> Arguments Resolver 가 JSON을 DTO(Comment)로 자동 변경 해줌 
		//   (JACKSON 라이브러리 기능)
		// HttpMessageConverter는 내보낼 때 JSON으로 바꿔줌
		return service.insert(comment);
	}
	
	/** 댓글 수정
	 * @param comment (번호, 내용)
	 * @return result
	 */
	@PutMapping("")
	public int update( @RequestBody Comment comment) {
		return service.update(comment);
	}
	
	/** 댓글 삭제
	 * @return result
	 */
	@DeleteMapping("")
	public int delete(@RequestBody int commentNo ) {
		
		
		return service.delete(commentNo);
	}
	
	
	
}
	
