package edu.kh.project.myPage.model.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

	@Autowired
	private BCryptPasswordEncoder bcrypt; // 권장되지 않는 방법
	// private final BCryptPasswordEncoder bcrypt;
	
	private final MyPageMapper mapper;

	// @RequiredArgsConstructor 를 이용했을 때 자동 완성 되는 구문
//	@Autowired
//	public MyPageServiceImpl(MyPageMapper mapper) {
//		this.mapper = mapper;
//	}

	// 회원 정보 수정
	@Override
	public int updateInfo(Member inputMember, String[] memberAddress) {

		// 입력된 주소가 있을 경우
		// memberAddress를 A^^^B^^^C 형태로 가공

		// 주소 입력 X -> inputMember.getMemberAddress() -> ",,"

		if (inputMember.getMemberAddress().equals(",,")) {

			// 주소에 null 대입
			inputMember.setMemberAddress(null);

		} else {
			// memberAddress를 A^^^B^^^C 형태로 가공

			String address = String.join("^^^", memberAddress);

			// 주소에 가공된 데이터 대입
			inputMember.setMemberAddress(address);
		}

		// SQL 수행 후 결과 반환
		return mapper.updateInfo(inputMember);
	}

	// 비밀 번호 수정
	@Override
	public int updatePw(int memberNo, String currentPw, String newPw) {

		// DB에서 암호화된 비밀 번호를 조회 , 누구인지 구분할 수 있는 memberNo 보내줌
		String pw = mapper.selectPw(memberNo);

		// Map으로 가져왔을 때 하나씩 꺼내면 Object로 인식해서 String으로 형변환 시키기
		
		if (!bcrypt.matches(currentPw, pw)) {

			return 0;
		}

		
		// 새 비밀번호를 암호화 진행
		String encPw = bcrypt.encode(newPw);
		 
		 
		// 성공시

		// 새 Map 객체 생성
		Map<String, Object> map = new HashMap<>();

		map.put("memberNo", memberNo);
		map.put("encPw", encPw);

		int result = mapper.updatePw(map);

		return result;
	}
	
	// 회원 탈퇴
	@Override
	public int deletePw(int memberNo, String memberPw) {
		
		// 현재 로그인한 회원의 암호화된 비밀번호를 DB에서 조회
		String pw = mapper.selectPw(memberNo);
		
		if (!bcrypt.matches(memberPw, pw)) {

			return 0;
		}

		
	// 같을 때  MEMBER_DEL_FL을 'Y'로 업데이트 변경하는 매퍼 호출
		int result = mapper.deletePw(memberNo);
		
		return result;
		
	}

	// 파일 업로드 테스트1
	@Override
	public String fileUpload1(MultipartFile uploadFile) throws IllegalStateException, IOException {
		
		// MultipartFile 이 제공하는 메서드
		// - getSize() : 파일 크기
		// - isEmpty() : 업로드한 파일이 없을 경우 true
		// - getOriginalFileName() : 원본 파일 명
		// - transferTo (경로) : 
		//   메모리 또는 임시 저장 경로에 업로드된 파일을
		//   원하는 경로에 전송(서버 어떤 폴더에 저장할지 지정)
		
		if(uploadFile.isEmpty()) { // 업로드한 파일이 없을 경우
			return null;
			
		}
		
		// 업로드한 파일이 있을 경우
		// C:\\uploadFiles\\test\\파일명으로 서버에 저장
		uploadFile.transferTo(
				new File("C:\\uploadFiles\\test\\" + uploadFile.getOriginalFilename())); 
		
		
		// 웹에서 해당 파일에 접근할 수 있는 경로를 반환
		
		// 서버 : C:\\uploadFiles\\test\\a.jpg
		// 웹 접근 주소 : /myPage/file/a.jpg
		return "/myPage/file/" + uploadFile.getOriginalFilename();
	}
}
