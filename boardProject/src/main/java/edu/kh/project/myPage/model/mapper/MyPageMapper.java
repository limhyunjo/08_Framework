package edu.kh.project.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.dto.UploadFile;

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

	/** 파일 정보를 DB에 삽입
	 * @param uf
	 * @return
	 */
	int insertUploadFile(UploadFile uf);

	/** 파일 목록 조회
	 * @return
	 */
	List<UploadFile> fileList();

	/** 프로필 이미지 변경
	 * @param mem
	 * @return
	 */
	int profile(Member mem);

}
