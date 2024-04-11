package edu.kh.project.board.model.service;

import java.util.List;
import java.util.Map;

public interface BoardService {

	// interceptor 호출하는 코드 작성할 것임
	
	// Map 이용
	
	// Map은 k : v로 저장해서 옴 {"boardCode" : 1, 
	//                                         "boardName": "공지게시판"}, <- 이거 하나가 Map으로 list에 묶어서 가져옴
	//                                        {"boardCode" : 1, 
	//                                         "boardName": "공지게시판"}, 
	// 										   {"boardCode" : 1, 
	//                                         "boardName": "공지게시판"}
	// list의 요소가 Map
	
	/** 게시판 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 특정 게시판의 지정된 페이지 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);
	

	
	
}
