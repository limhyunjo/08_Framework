package edu.kh.project.board.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImg;
import edu.kh.project.board.model.service.BoardService;
import edu.kh.project.member.model.dto.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	
	/** 게시글 목록 조회 + 검색( name 속성의 key, query가 추가로 제출됨)
	 * @param boardCode : 게시판 종류 구분
	 * @param cp: (current page) 현재 조회 요청한 페이지 (없으면 1)
	 * @param paramMap : 제출된 파라미터가 모두 저장된 Map
	 * 										( 검색 시 key랑 query가  담겨 있음)
	 * 
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
			Model model, // reqeust scope로 값을 전달하는 용도
			
			
			@RequestParam Map<String, Object> paramMap
			
			
			// Get매핑은 주소의 쿼리 스트링에 param이 담겨옴
			// cp의 값이 있으면 그 페이지를 보고 아니면 1페이지 보기
			) { 
		
		log.debug("boardCode: " + boardCode);
		
		
		// 조회 서비스 호출 후 결과 반환
		Map<String, Object> map = null;
		
		// 검색이 아닌 경우
		if(paramMap.get("key") == null) {
			map = service.selectBoardList(boardCode, cp);
			
		} else { // 검색인 경우
			
			// boardCode를 paramMap에 추가
			paramMap.put("boardCode", boardCode);
			
			// 검색 서비스 호출 // map으로 반환 받아 돌아올 것임
			map = service.searchList(paramMap, cp);
			
		}
			
		// 몇번 게시판 몇 페이지를 조회할 것인지 알려주기 위해 boardCode, cp 보냄
		// boardCode : 게시판 번호 
		// cp 현재 페이지 
		// -> 어느 게시판의 몇번 페이지인지 알기 위해 보냄
		
		// 동작 실행이 안되면 a태그가 잘못되었거나 pathVariable이 잘못 써짐
		
		// Map으로 묶은 이유는 메서드 반환을 하나만 할 수 있어서 하나로 묶어서 보내고
		// 사용할 때는 다시 쪼갬
		
		// pagination이랑 boardList를 담아서 전달함

		model.addAttribute("pagination",map.get("pagination"));
		model.addAttribute("boardList",map.get("boardList"));
		
		// 주소에 쿼리스트링으로 치고 디버그로 확인 http://localhost/board/1?cp=1
		
		return "board/boardList"; //boardList.html로 forward
	}
	
	// 상세 조회 요청 주소
	//    /board/1/1998?cp=1
	//    /board/2/1934?cp=2
	
	//  모든 숫자 정규식 써줌  
	
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(
			@PathVariable("boardCode") int boardCode,
			@PathVariable("boardNo") int boardNo,
			Model model, 
			RedirectAttributes ra,
			@SessionAttribute(value = "loginMember", required = false) Member loginMember,
			HttpServletRequest req,  // 요청에 담긴 쿠키 얻어오기
			HttpServletResponse resp // 새로운 쿠키를 만들어 응답하기
			// login 안하면 에러 뜸
			// 400 에러 컨트롤러 잘못됨
			) throws ParseException {
		
		//@SessionAttribute(value = "loginMember", required = false) Member loginMember
		// - @SessionAttribute : Session에서 속성 값 얻어오기
		// - value = "loginMember" : 속성의 key 값 loginMember
		// - required = false : 필수 X (없어도 오류 X)
		// -> 해당 속성 값이 없으면 null 반환
	
		
		// 게시글 상세 조회 서비스 호출
		
		// 1) Map으로 전달할 파라미터 묶기
		Map<String, Integer> map = new HashMap<>();
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		

		// 로그인 상태인 경우에만 memberNo 추가
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		
		// 2) 서비스 호출
		
		Board board = service.selectOne(map);
		
		String path = null;
		
		// 조회 결과가 없는 경우
		if(board==null) {
			path="redirect:/boar/" + boardCode; //  목록 재요청
		    ra.addFlashAttribute("message", "게시글이 존재하지 않습니다");
		}
		// 조회 결과가 있을 경우
		else {
			
			/* ********* 쿠키를 이용한 조회수 증가 (시작) *************/
			
			// 1. 비회원 또는 로그인한 회원의 글이 아닌 경우
			//    ( 글쓴이를 뺀 다른 사람)
			
			if(loginMember == null || 
				loginMember.getMemberNo() != board.getMemberNo()) {
				
				// 요청에 담겨있는 모든 쿠키 얻어오기
				Cookie[]  cookies = req.getCookies(); 
				
				Cookie c = null;
				
				for (Cookie temp : cookies){
					
					// 요청에 담긴 쿠키에 "readBoardNo"가 존재할 때
					if(temp.getName().equals("readBoardNo")) {
						
						c= temp;
						
						break;
					}
				}
				
	           int result = 0; // 조회수 증가 결과를 저장할 변수
				
				// "readBoardNo"가 요청 받은 쿠키에 없을 때
				if(c == null) {
					
					// 새 쿠키 생성("readBoardNo", [게시글번호])
					c = new Cookie("readBoardNo", "[" + boardNo + "]");
					result = service.updateReadCount(boardNo);
				} 
				
				// "readBoardNo"가 요청 받은 쿠키에 존재할 때
				else {
					// ("readBoardNo", [2][30][400][2000])
					
					// 현재 글을 처음 읽은 경우
					if(c.getValue().indexOf("[" + boardNo + "]") == -1) {
						
						// 해당 글 번호를 쿠키에 누적
						c.setValue(c.getValue() + "[" + boardNo + "]");
						result = service.updateReadCount(boardNo);
					}
				}
			
			 // 조회수 증가 성공 시
				
			if(result >0) {
				
				// 먼저 조회된 board의 readCount 값을
				// result 값으로 변환
				
				board.setReadCount(result);
				
				// 적용 경로 설정
				c.setPath("/"); // "/" 이하 경로 요청 시 쿠키 서버로 전달

				// 수명 지정
				Calendar cal = Calendar.getInstance(); // 싱글톤 패턴
				cal.add(cal.DATE, 1);

				// 날짜 표기법 변경 객체 (DB의 TO_CHAR()와 비슷)
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				// java.util.Date
				Date a = new Date(); // 현재 시간

				Date temp = new Date(cal.getTimeInMillis()); // 다음날 (24시간 후)
				// 2024-04-15 12:30:10

				Date b = sdf.parse(sdf.format(temp)); // 다음날 0시 0분 0초

				// 다음날 0시 0분 0초 - 현재 시간
				long diff = (b.getTime() - a.getTime()) / 1000;
				// -> 다음날 0시 0분 0초까지 남은 시간을 초단위로 반환

				c.setMaxAge((int) diff); // 수명 설정

				resp.addCookie(c); // 응답 객체를 이용해서 클라이언트에게 전달
				
				// 한 
				
				
				//브라우저로 올릴 수 있는 조회수는 하루 한 번
			}
				
			}
			
			/* ********* 쿠키를 이용한 조회수 증가 (끝)  *************/
			
			
			path ="board/boardDetail";
			
			// board - 게시글 상세 조회 + imageList + commentList
			model.addAttribute("board", board);
			
			// 조회된 이미지 목록(imageList)가 있을 경우
			if(!board.getImageList().isEmpty()) {
				
				BoardImg thumbnail = null;
				
				// List의 0번 index 얻어와라
				// imageList의 0번 인덱스 == 가장 빠른 순서 (imgOrder)
				//이미지 목록의 첫 번째 행이 순서 0 == 썸네일인 경우
				if(board.getImageList().get(0).getImgOrder()==0) {
					
					thumbnail = board.getImageList().get(0);
				}
					
				
				// 썸네일이 있을 때 / 없을 때
				// 출력되는 이미지 시작 인덱스 지정하는 코드
				// (썸네일 제외하고 인덱스 계산)
				model.addAttribute("thumbnail",thumbnail);			
				model.addAttribute("start", thumbnail != null ? 1:0);
				// thumbnail이 있으면 1
				// thumbnail이 없으면 start에 0이 들어감
				
			}
			
		}
		
		return path;
	}
	
	// Json : java script object notaion "{k:v, k:v}" (String)
	
	/**게시글 좋아요 체크 / 해제
	 * @return count
	 */
	@ResponseBody // 값을 그대로 돌려보냄
	 @PostMapping("like")
	 public int boardLike(
			 @RequestBody Map<String,Integer> map ) {
		// object 타입으로 HttpConverter가 바꿔줌
			 
			 return service.boardLike(map);
		 
	 }
	
	
	// 1. 조회할 때 마다 증가
	// 2. DB에 누가 어떤 글을 조회 했는가
	//     일정 기간 단위로 확인해서 증가
	// 3. local / session 스토리지 ( 브라우저에서만 사용 가능)
	// -> JS 사용 가능, 서버 사용 X
	
	// 4. 쿠키를 이용한 조회 수 증가
	// 한번 읽은 게시물 번호 저장하여 조회수가 더 늘어나지 않게 하기
	
	// 5. in Memory DB  (redis)
	
	
	
	
	
	
}
