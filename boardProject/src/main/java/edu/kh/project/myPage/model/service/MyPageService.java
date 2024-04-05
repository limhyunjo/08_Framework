package edu.kh.project.myPage.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.dto.UploadFile;

public interface MyPageService {

	/** 회원 정보 수정
	 * @param inputMember
	 * @param memberAddress
	 * @return result 
	 */
	int updateInfo(Member inputMember, String[] memberAddress);

	/** 비밀번호 수정
	 * @param memberNo
	 * @param newPw 
	 * @param currentPw 
	 * @return
	 */
	int updatePw(int memberNo, String currentPw, String newPw);

	/** 회원 탈퇴
	 * @param memberNo
	 * @param memberPw
	 * @return
	 */
	int deletePw(int memberNo, String memberPw);

	/** 파일 업로드
	 * @param uploadFile
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	String fileUpload1(MultipartFile uploadFile) throws IllegalStateException, IOException;

	/** 파일 업로드2
	 * @param uploadFile
	 * @param memberNo
	 * @return
	 */
	int fileUpload2(MultipartFile uploadFile, int memberNo)throws IllegalStateException, IOException;

	/** 파일 목록 조회
	 * @return
	 */
	List<UploadFile> fileList();

	/** 여러 파일 업로드
	 * @param aaaList
	 * @param bbbList
	 * @param memberNo
	 * @return
	 */
	int fileUpload3(
		List<MultipartFile> aaaList, 
		List<MultipartFile> bbbList, 
		int memberNo) throws IllegalStateException, IOException ;

	/** 프로필 이미지 변경
	 * @param profileImg
	 * @param loginMember
	 * @return
	 */
	int profile(MultipartFile profileImg, Member loginMember)throws IllegalStateException, IOException;



}

