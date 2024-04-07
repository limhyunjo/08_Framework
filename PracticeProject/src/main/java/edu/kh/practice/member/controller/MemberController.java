package edu.kh.practice.member.controller;




/*@SessionAttributes({"key", "key",....})
 * - Model에 추가된 속성(Attribute) 중
 * key 값이 일치하는 속성을 session scope로 변경
 * */


public class MemberController {

	
	/*로그인
	 * - 특정 사이트에 아이디/ 비밀번호 등을 입력해서
	 *   해당 정보가 있으면 조회/서비스 이용
	 *   
	 *  - 로그인한 정보를 session에 기록하여
	 *    로그아웃 또는 브라우저 종료 시 까지
	 *    해당 정보를 계속 이용할 수 있게 함
	 * */
	
	// 커맨드 객체
	// - 요청 시 전달 받은 파라미터를 
	// 같은 이름의 필드에 세팅한 객체
	
	
	
	
	
}

/* Cookie란?
 * - 클라이언트 측(브라우저)에서 관리하는 데이터(파일 형식)
 * 
 * - Cookie에는 만료기간, 데이터(key=value), 사용하는 사이트(주소)
 *  가 기록되어 있음
 *  
 * - 클라이언트가 쿠키에 기록된 사이트로 요청으로 보낼 때
 *   요청에 쿠키가 담겨져서 서버로 넘어감
 *   
 * - Cookie의 생성, 수정, 삭제는 Server가 관리
 *   저장은 Client가 함
 *   
 * - Cookie는 HttpServletResponse를 이용해서 생성,
 *   클라이언트에게 전달(응답) 할 수 있다
 */





