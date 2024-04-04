package edu.kh.project.myPage.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

@Mapper
public interface MyPageMapper {

	/** 회원정보 수정
	 * @param inputMember
	 * @return result
	 */
	int updateInfo(Member inputMember);

	/** 비밀 번호 대조
	 * @param memberNo
	 * @return
	 */
	String selectPw(int memberNo);

	/**비밀번호 수정
	 * @param memberNo
	 * @param encPw
	 * @return 
	 */
	int updatePw(Map<String, Object> map);

	/** 회원 탈퇴
	 * @param memberNo
	 * @return
	 */
	int deletePw(int memberNo);

}
