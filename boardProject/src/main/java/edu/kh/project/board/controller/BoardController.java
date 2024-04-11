package edu.kh.project.board.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.project.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;
	
	
	// @PathVariable : 주소를 변수로 사용 // 최근 많이 사용하는 코드
	
	//  @PathVariable("key") : @Request/Get/Post/put/DeleteMapping()에 작성된
	//                                   URL에서 특정 경로를 얻어와 변수에 저장하는 어노테이션 
	
	//  ex) 요청 주소 :/test/영주/하와이
	//          @GetMapping("test/{apple}/{banana}"}
	//              public String test(
	//               @PathVariable("apple") String a
	//               @PathVariable("banana") String b
	//         }
	//          a에 저장된 값 : 영주
	//          b에 저장된 값 : 하와이
	
   // [추가 내용]
	// 요청 주소에 정규 표현식을 사용해서
    // 요청 주소를 제한할 수 있다!!!
	
	// ex)  @GetMapping("{boardCode:[0-9]+}") 
	// boardCode 자리에 숫자로만 된 주소만 매핑한다는 뜻
	
	// @PathVariable 은 공통 주소 board 뒤에 하위 주소가 있을 경우 다 읽어옴
	
	/** 게시글 목록 조회
	 * @param boardCode : 게시판 종류 구분
	 * @param cp: (current page) 현재 조회 요청한 페이지 (없으면 1)
	 * @return
	 * 
	 * - /board/000
	 *   /board 이하 1레벨 자리에 숫자로된 요청 주소가 
	 *   작성되어 있을 때만  동작하도록 만드는 법 
	 *   -> 정규 표현식 이용
	 *   
	 *   :[0-9] : 한 칸에 0~9 사이 숫자 입력 가능
	 *   + : 하나 이상
	 *   
	 *   [0-9]+ : 모든 숫자
	 *   
	 *   -> 숫자 이외를 쓰면 404 에러가 뜬다 
	 */
	@GetMapping("{boardCode:[0-9]+}") 
	public String selectBoardList(
			@PathVariable("boardCode") int boardCode,// 주소를 옆의 변수에 저장하겠다
			@RequestParam(value="cp", required = false, defaultValue="1") int cp ,
			Model model // reqeust scope로 값을 전달하는 용도
			
			// Get매핑은 주소의 쿼리 스트링에 param이 담겨옴
			// cp의 값이 있으면 그 페이지를 보고 아니면 1페이지 보기
			) { 
		
		log.debug("boardCode: " + boardCode);
		
		// 조회 서비스 호출 후 결과 반환
		Map<String, Object> map = service.selectBoardList(boardCode, cp);
		// 몇번 게시판 몇 페이지를 조회할 것인지 알려주기 위해 boardCode, cp 보냄
		// boardCode : 게시판 번호 
		// cp 현재 페이지 
		// -> 어느 게시판의 몇번 페이지인지 알기 위해 보냄
		
		// 동작 실행이 안되면 a태그가 잘못되었거나 pathVariable이 잘못 써짐
		
		// Map으로 묶은 이유는 메서드 반환을 하나만 할 수 있어서 하나로 묶어서 보내고
		// 사용할 때는 다시 쪼갬
		model.addAttribute("pagination",map.get("pagination"));
		model.addAttribute("boardList",map.get("boardList"));
		
		// 주소에 쿼리스트링으로 치고 디버그로 확인 http://localhost/board/1?cp=1
		
		return "board/boardList"; //boardList.html로 forward
	}
	

}
