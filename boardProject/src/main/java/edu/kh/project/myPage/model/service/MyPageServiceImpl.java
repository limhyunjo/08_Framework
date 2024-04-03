package edu.kh.project.myPage.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
