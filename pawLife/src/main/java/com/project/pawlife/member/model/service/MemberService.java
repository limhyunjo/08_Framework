package com.project.pawlife.member.model.service;

import com.project.pawlife.member.model.dto.Member;

public interface MemberService {

	/** 로그인 서비스
	 * @param inputMember
	 * @return loginMember 
	 */
	Member login(Member inputMember);

}
