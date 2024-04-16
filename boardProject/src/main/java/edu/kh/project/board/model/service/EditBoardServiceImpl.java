package edu.kh.project.board.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.board.model.BoardInsertException;
import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImg;
import edu.kh.project.board.model.mapper.EditBoardMapper;
import edu.kh.project.common.util.Utility;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional // 예외 발생 시 롤백해주는 어노테이션 checkedException은 안 해주고 Runtime만 해줌
@PropertySource("classpath:/config.properties")
public class EditBoardServiceImpl implements EditBoardService{

	private final EditBoardMapper mapper;
	
	// config.properties 값을 얻어와 필드에 저장 (유지 보수를 위해 다른 파일에 써서 읽어옴)
	@Value("${my.board.web-path}")
	private String webPath;
	
	@Value("${my.board.folder-path}")
	private String folderPath;
	
	
	// 게시글 작성
	@Override
	public int boardInsert(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException {
		
		// 1. 게시글 부분(inputBoard)을 먼저
		//    BOARD 테이블에 INSERT 하기
		//    -> INSERT 결과로 작성된 게시글 번호(생성된 시퀀스 번호) 반환 받기
		
		
		int result = mapper.boardInsert(inputBoard); 
		
		// result == INSERT 결과 (0/1 행 삽입) 
		
		// mapper.xml에서 <selectKey> 태그를 이용해 생성된
		// boardNo가 inputBoard에 저장된 상태!! (얕은 복사 개념 이해 필수!!)
		
		// 삽입 실패 시
		if(result ==0) return 0;
		
		// 삽입된 게시글의 번호를 변수로 저장
		int boardNo =  inputBoard.getBoardNo(); // selectKey가 세팅해준 boardNo
		
		
		// 2. 업로드된 이미지가 실제로 존재할 경우
		//     업로드된 이미지만 별도로 저장하여
		//     "BOARD_IMG" 테이블에 삽입하는 코드 작성
		
		//  실제 업로드된 이미지의 정보를 모아둘 List 생성
		List<BoardImg> uploadList = new ArrayList<>(); 
		// List타입 추론으로 ArrayList< >를 안써도 됨
		
		//images 리스트에서 하나씩 꺼내어 선택된 파일이 있는지 검사
		for(int i = 0 ; i< images.size() ; i++) { // List는 length가 아니라 size
			
			if( !images.get(i).isEmpty() ) {
				
				// IMG_PATH == webPath
				// BOARD_NO == boardNo
				// IMG_ORDER == i (인덱스 == 순서)
				
				// 원본명
				String originalName = images.get(i).getOriginalFilename();
				
				// 변경명
				String rename = Utility.fileRename(originalName);
				
				// 모든 값을 저장한 DTO 객체 생성 (Builder 패턴 사용)
				BoardImg img = BoardImg.builder()
						                .imgOriginalName(originalName)
						                .imgRename(rename)
						                .imgPath(webPath)
						                .boardNo(boardNo)
						                .imgOrder(i)
						                .uploadFile(images.get(i))
						                .build();
				
				uploadList.add(img); // 업로드 리스트가 만들어짐
				
			}
		}
		
		//실제 선택한 파일이 없을 경우
		if(uploadList.isEmpty()) {
			return boardNo;
		}
		
		// 선택할 파일이 존재할 경우
		// -> "BOARD_IMG" 테이블에 INSERT + 서버에 파일 저장
		
		/* 여러 행 삽입 방법 */
		// 1) 1행 삽입하는 SQL을 for문을 이용해서 여러 번 호출
		
		// 2) 여러 행을 삽입하는 SQL을 1회 호출 (이거 사용할 것임!!!)
		
		
		//result == 삽입된 행의 개수 == uploadList.size() 
		result = mapper.insertUploadList(uploadList); // 삽입된 행의 개수 반환
		
		// 다중 INSERT 성공 확인
		// (uploadList에 저장된 값이 모두 삽입 되었나 확인)
		if(result == uploadList.size()) {
			// 서버에 팡리 저장
			for(BoardImg img : uploadList) {
				
				// 실제 업로드된 파일을 임시에서 진짜 저장할 경로로 바꿈
				 img.getUploadFile()
				 .transferTo(new File(folderPath + img.getImgRename() ));
			}
			
		}else {
			// 부분적으로 삽입 실패한 경우 -> 전체 서비스 실패로 판단
			// -> 이전에 삽입된 내용 모두 rollback
			// -> rollback 하는 방법 
			//     == RuntimeException 강제 발생 (@Transactional)
			
			throw new BoardInsertException("이미지가 정상 삽입되지 않음");
		}
		
		//게시글 번호 돌려보내줌
		return boardNo;
	}


	@Override
	public int boardDelete(int boardCode, int boardNo, int memberNo) {
		
	Map<String, Object> map = new HashMap<>();
	
	map.put("memberNo", memberNo);
	map.put("boardCode", boardCode);
	map.put("boardNo", boardNo);
	
		return mapper.boardDelete(map);
	}
}
