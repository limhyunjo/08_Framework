package edu.kh.project.websocket.interceptor;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import jakarta.servlet.http.HttpSession;

/* SessionHandshakeInterceptor 클래스
 * 
 *  WebsocketHandler가 동작하기 전/ 후에 
 *  연결된 클라이언트에 세션을 가로채는 동작을 작성할 클래스
 * */

@Component // bean 등록
public class SessionHandshakeInterceptor implements HandshakeInterceptor{

	// Handler 동작 전에 수행되는 메서드 ( 전처리 ) -> 보통 이걸 많이 씀
	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		
		//ServerHttpRequest : HttpServletRequest의 부모 인터페이스
		// ServerHttpResponse : HttpServletResponse의 부모 인터페이스
		//attributes : 해당 맵에 세팅된 속성 (데이터)은
		//                다음에 동작할 Handler 객체에게 전달됨 / 데이터 전달 용도 Model이랑 비슷
		//                (HandshakeInterceptor -> Handler 데이터 전달하는 역할 )
		//               여러 데이터 전달할 수 있도록 Map으로 준비
		
		
		// instanceof : 참조하는 객체 확인 
		// request 객체가 참조하는 객체가 ServletServerHttpRequest가 맞는지
		// ServletServerHttpRequest로 다운캐스팅이 가능한가?
		if(request instanceof ServletServerHttpRequest) {
			
			// 다운 캐스팅
			ServletServerHttpRequest servletRequest
			= (ServletServerHttpRequest) request;
			
			// 웹소켓 동작을 요청한 클라이언트의 세션을 얻어옴 (가로챔)
			HttpSession session 
			= servletRequest.getServletRequest().getSession();
			
			// 가로챈 세션을 Handler에 전달할 수 있게 값 세팅
			attributes.put("session", session);
			
					
		}
		
		return true; // 가로채기 진행 여부 true
		// true 로 작성해야 세션 가로채서 Handler에게 전달 가능
	}

	// Handler 동작 후에 수행되는 메서드 ( 후처리 )
	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {

		
	}
	
	
	
    
	
	
}


/*webSocket : 
- 브라우저(클라이언트)와 웹 서버 간의 전이중 통신을 지원하는 프로토콜

클라이언트와 서버를 연결 ( 서버에 클라이언트(세션객체) 목록이 저장됨 )
응답이 요청한 클라이언트와 다른 클라이언트에게 보내짐

(전이중 통신 예시 : 전화 (양방향 통신 지원) )
나 <-> 기지국 <-> 다른 사람
클라이언트1 <-> 서버 <-> 클라이언트2

- HTML5 부터 지원하는 기능
- 자바는 7부터 지원했으나 8버전 부터 본격적으로 지원
- Spring은 4버전 부터 지원

--------------------------------------------------------------------------------

[ 클라이언트 사이드 ] 
- SockJS 이용 ( 웹소켓을 간단히 작성 
                    + 웹소켓 지원 X 브라우저에서 웹소켓 사용 가능하게 하는 
                    JS 라이브러리 )
  

[ 서버 사이드 ] 
- WebsocketConfig 클래스 만들어서 사용 
-> 웹소켓 요청 주소 지정 
-> Http 요청에서 Session을 가로챌 객체를 등록 

- SessionHandshakeInterceptor 클래스 
-> 웹소켓 요청 처리 코드에 사용될 클라이언트 세션을 가로채는 코드 작성

- 000WebSocket Handler 클래스
-> 웹소켓 동작 시 수행할 구문을 작성하는 클래스
-> 웹소켓에 연결된 세션을 모아두고, 
     요청에 따라 알맞은 세션에 값을 전달할 수 있는 코드 작성
 

 * 
 * 
 * 
 */