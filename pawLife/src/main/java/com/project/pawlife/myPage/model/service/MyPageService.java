package com.project.pawlife.myPage.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.review.model.dto.Review;

public interface MyPageService {
	
	
	/** 로그인한 회원이 작성한 입양 게시글 조회
	 * @param memberNo
	 * @param cp 
	 * @return adoptList
	 */
	Map<String, Object> selectAdopt(int memberNo, int cp);

	/** 로그인한 회원이 쓴 후기 게시글 조회
	 * @param memberNo 
	 * @param cp 
	 * @return reviewList
	 */
	Map<String, Object> selectReview(int memberNo, int cp);

	/** 로그인한 회원이 쓴 댓글 조회
	 * @param memberNo
	 * @param cp 
	 * @return commentList
	 */
	Map<String, Object> selectComment(int memberNo, int cp);
	
	/** 로그인한 회원이 북마크한 게시물 조회
	 * @param memberNo
	 * @param cp 
	 * @return bookmarkList
	 */
	Map<String, Object> selectBookMark(int memberNo, int cp);
	
	
	/** 개인정보 수정 (닉네임/ 전화번호 )
	 * @param inputMember
	 * @return
	 */
	int profileUpdate(Member inputMember);


	/** 비밀번호 수정
	 * @param memberNo
	 * @param currentPw
	 * @param newPw
	 * @return result
	 */
	int changeMemberPw(int memberNo, String currentPw, String newPw);


	/** 프로필 이미지 변경
	 * @param profileImg
	 * @param loginMember
	 * @param statusCheck 
	 * @return result
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	int profile(MultipartFile imageInput, Member loginMember, int statusCheck) throws IllegalStateException, IOException;

	
	/** 회원 탈퇴
	 * @param memberNo
	 * @return
	 */
	int deleteMember(int memberNo);

	/** 로그인한 회원이 작성한 입양 게시글의 수정 페이지로 이동
	 * @param map
	 * @return
	 */
	Adopt selectOneAdopt(Map<String, Integer> map);

	/** 로그인한 회원이 작성한 입양 게시글( 마이페이지 입양 리스트)에서 입양 완료 버튼을 누른 경우
	 * @param memberNo
	 * @return
	 */
	int adoptDel(int memberNo);

	/** 로그인한 회원이 작성한 입양 게시글 완료 여부 변경
	 * @param memberNo 
	 * @param adopt
	 * @return
	 */
	int changeAdoptComplete(int memberNo, Adopt adopt);



}
