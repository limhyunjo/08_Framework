package com.project.pawlife.myPage.model.service;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.common.util.Utility;
import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.myPage.model.mapper.MyPageMapper;
import com.project.pawlife.review.model.dto.Review;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties") // config에서 가져와서 쓰겠다는 의미
public class MyPageServiceImpl implements MyPageService{

	
	private final MyPageMapper mapper;
	
	private final BCryptPasswordEncoder bcrypt;
	
	@Value("${my.profile.web-path}")  // /myPage/profile/
	private String profileWebPath;
	
	@Value("${my.profile.folder-path}") // D:/uploadFiles/profile/
	private String profileFolderPath;
	
	
	// 로그인한 회원이 작성한 입양 게시글 리스트 조회
	@Override
	public List<Adopt> selectAdopt(int memberNo) {
		
		return mapper.selectAdopt(memberNo);
	}


	// 로그인한 회원이 작성한 후기 게시글 리스트 조회
	@Override
	public List<Review> selectReview(int memberNo) {
		
		return mapper.selectReview(memberNo);
	}
	

	// 로그인한 회원이 작성한 댓글 리스트 조회
	@Override
	public List<Review> selectComment(int memberNo) {
		
		return mapper.selectComment(memberNo);
	}
	
	// 로그인한 회원이 북마크한 게시물 조회
	@Override
	public List<Adopt> selectBookMark(int memberNo) {
	
		return mapper.selectBookMark(memberNo);
	}
	
	// 개인 정보 수정(닉네임/전화번호)
	@Override
	public int profileUpdate(Member inputMember) {
		
		return mapper.profileUpdate(inputMember);
	}
	
	   // 개인 정보 수정 (비밀번호)
		@Override
		public int changeMemberPw(int memberNo, String currentPw, String newPw) {
			
			// 암호화된 비밀번호 조회
			String pw = mapper.selectPw(memberNo); 
			
			
			 if(!bcrypt.matches(currentPw, pw)) {
				 
				 return 0;
			 }
			 
			 // 새 비밀번호 암호화
			 String encPw = bcrypt.encode(newPw);
			 
			 // 성공하면 새 Map 객체 생성
			 Map<String, Object> map = new HashMap<>();
			 
			 map.put("memberNo", memberNo);
			 map.put("encPw", encPw);
			 
			 
			 int result = mapper.changeMemberPw(map);
			
			
			return result;
		}




	// 프로필 이미지 변경
	@Override
	public int profile( MultipartFile imageInput, Member loginMember, int statusCheck) throws IllegalStateException, IOException{
		
		// 수정할 경로
		String updatePath = null;
		
		// 변경명 저장
		String rename = null;
		
		if(statusCheck==-1) {
			loginMember.setProfileImg("none");
		}
		
		// 업로드한 이미지가 있을 경우
		if( !imageInput.isEmpty()) {
			
			// updatePath 조합
			
			// 파일명 변경
			rename = Utility.fileRename(imageInput.getOriginalFilename());
			
			
			//  /myPage/profile/변경된 파일명.jpg
			updatePath = profileWebPath + rename;
			
		}
		
		// 수정된 프로필 이미지 경로  + 회원 번호를 저장할 DTO 객체
		Member mem = Member.builder()
				              .memberNo(loginMember.getMemberNo())
				              .profileImg(updatePath)
				              .build();
		
		// UPDATE 수행
		int result = mapper.profile(mem);
		
		if(result>0) { // 수정 성공 시
			
			// 프로필 이미지를 없앤 경우(NULL로 수정한 경우) 를 제외
			// -> 업로드한 이미지가 있을 경우
			if(!imageInput.isEmpty()) {
			
			// 파일을 서버에 지정된 폴더에 저장
				imageInput.transferTo(new File(profileFolderPath + rename ));
			
			}
			// 세션 회원 정보에서 프로필 이미지 경로를 
			// 업데이트한 경로로 변경
			loginMember.setProfileImg(updatePath);
		}
		return result;
	}



	// 회원 탈퇴
	@Override
	public int deleteMember(int memberNo) {
		
		int result = mapper.deleteMember(memberNo);
		
		return result;
	}

    // 로그인한 회원이 작성한 입양 게시글 수정 페이지로 이동
	@Override
	public Adopt selectOneAdopt(Map<String, Integer> map) {
		
		return mapper.selectOneAdopt(map);
	}

   // 로그인한 회원이 작성한 입양 게시글( 마이페이지 입양 리스트)에서 입양 완료 버튼을 누른 경우
	@Override
	public int adoptDel(int memberNo) {
		
		int result = mapper.adoptDel(memberNo);
		
		
		return result;
	}



	







}
