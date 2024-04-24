package com.project.pawlife.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.pawlife.member.model.dto.Member;

@Mapper // 자동으로 SQL을 상속 받아서 bean으로 등록 해줌
public interface MemberMapper {

	/** 로그인 SQL 실행
	 * @param memberEmail
	 * @return loginMember
	 */
	public Member login(String memberEmail);
}
