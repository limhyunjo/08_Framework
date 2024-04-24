package com.project.pawlife.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Transactional // 예외 발생 시 rollback 해줌
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	// Autowired는 등록된 bean 중에서 같은 타입 또는 상속관계인  bean을
	// 자동으로 의존성 주입해서 쓸 수 있게 해줌 
	// 요즘 추천하는 방법은 아님
	
    private final MemberMapper mapper;

    // BCrypt 암호화 객체 가져옴 (security configue에서 가져옴)
    @Autowired
    private BCryptPasswordEncoder bcrypt;

	// 로그인 서비스
	@Override
	public Member login(Member inputMember) {
		
		
		// bcrypt.encode(문자열) : 문자열을 암호화하여 반환
		String bcryptPassword = bcrypt.encode(inputMember.getMemberPw());
		//-> 로그인 화면에서 아이디 비밀번호 치면 디버그 시 암호화 되어서 넘어옴
		
		// 1. 이메일이 일치하면서 탈퇴하지 않은 회원 조회
		// select 조회해서 loginMember에 담음
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		
		// 2. 일치하는 이메일이 없어서 조회 결과가 null인 경우
		
		if(loginMember ==null) return null;
		
		
		// 3. 입력 받은 비밀번호 (inputMember.getMemberPw()) 평문 상태
		// 암호화된 비밀번호 (loginMember.getMemberPw()) 
		// 위의 둘을 얻어와서 비교 -> 일치하는지 확인
		
		// 일치하지 않으면 
		if(bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())){
			
			return null;
		}
		
		// 일치하면 통째로 반환
		// 암호화된 비밀번호가 들어있지만 
		//session에 비밀번호가 올라와있다는 것 자체가 좋지 않으므로
		// 로그인 결과에서 비밀번호를 제거해줌
		 loginMember.setMemberPw(null); 
		
		//-> controller로 돌아감
		return loginMember;
	}
	
}
