package edu.kh.project.main.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.kh.project.main.model.mapper.MainMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

	private final MainMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;// 암호화
	
	// 500 에러는 서버 오류
	
	// 비밀번호 초기화
		@Override
		public int resetPw(int inputNo) {
			
			String pw = "pass01!";
			
			String encPw =  bcrypt.encode(pw);
			
			
			Map<String, Object> map = new HashMap<>(); // 다형성 적용
			map.put("inputNo", inputNo);
			map.put("encPw", encPw);
			
			return mapper.resetPw(map);
		}
		
		
		// 회원 탈퇴 복구
	@Override
	public int resetSecession(int inputNo) {
		
		int pw = inputNo; // 이건 안써도 됨
		
		
		
		return mapper.resetSecession(pw);
	}
	
	
}
