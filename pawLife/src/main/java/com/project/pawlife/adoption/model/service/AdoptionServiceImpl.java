package com.project.pawlife.adoption.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.adoption.model.mapper.AdoptionMapper;

import com.project.pawlife.common.util.Utility;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdoptionServiceImpl implements AdoptionService {

	
	@Value("${my.profile.web-path}")
	private String profileWebPath; // /myPage/profile/

	@Value("${my.profile.folder-path}")
	private String profileFolderPath;
	
	@Autowired
	private final AdoptionMapper mapper;

	// 입양 게시글 작성
	@Override
	public int adoptionWrite(Adopt inputAdopt, MultipartFile thumnailImg){
		
		
		// 수정할 경로
				String updatePath = null;
				
				String rename = null;
				
				// 업로드한 이미지가 있을 경우
				if(!thumnailImg.isEmpty()) {
					
					// updatePath 조합
					
					// 파일명 조합
					rename = com.project.pawlife.common.util.Utility.fileRename(thumnailImg.getOriginalFilename());

					// /myPage/profile/변경된파일명.jpg
					updatePath = profileWebPath+rename;
					
				}
				
				// 수정된 프로필 이미지 경로 + 회원번호를 저장할 DTO 객체
				Adopt adopt = Adopt.builder()
							 .thumnail(updatePath)
							 .build();
				
				Map<String, Object> map = new HashMap<>();
				
				map.put("inputAdopt", inputAdopt);
				map.put("adopt", adopt);
		
		
		return mapper.adoptionWrite(map);
		
		}
}
