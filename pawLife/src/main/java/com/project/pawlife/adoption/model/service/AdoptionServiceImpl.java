package com.project.pawlife.adoption.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.adoption.model.mapper.AdoptionMapper;
import com.project.pawlife.common.exception.AdoptInsertException;
import com.project.pawlife.common.util.Pagination;
import com.project.pawlife.common.util.Utility;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class AdoptionServiceImpl implements AdoptionService {

	
	@Value("${my.adopt.web-path}")
	private String adoptWebPath; // /myPage/profile/

	@Value("${my.adopt.folder-path}")
	private String adoptFolderPath;
	
	
	// EmailConfig 설정이 적용된 객체(메일 보내기 가능)
	private final JavaMailSender mailSender;
		
	// 타임리프(템플릿 엔진)을 이용해서 html 코드 -> java로 변환
	private final SpringTemplateEngine templateEngine;
	
	@Autowired
	private final AdoptionMapper mapper;

	// 입양 게시글 작성
	@Override
	public int adoptionInsert(Adopt inputAdopt, MultipartFile thumnailImg,int memberNo){
		
		
		// 수정할 경로
		String updatePath = null;
		
		String rename = null;
		
		// 업로드한 이미지가 있을 경우
		if(!thumnailImg.isEmpty()) {
			
			// updatePath 조합
			
			// 파일명 조합
			rename = Utility.fileRename(thumnailImg.getOriginalFilename());

			// /myPage/profile/변경된파일명.jpg
			updatePath = adoptWebPath+rename;
			
			inputAdopt.setThumnail(updatePath);
			inputAdopt.setMemberNo(memberNo);
		}
				
		
		int result = mapper.adoptionInsert(inputAdopt);
		
		if(result > 0) {
			try {
				thumnailImg.transferTo(new File(adoptFolderPath + rename));
			} catch (Exception e) {
				e.printStackTrace();
				throw new AdoptInsertException("입양 게시글 이미지 삽입 중 예외 발생");
			}
		}
		
		
		return result;
		
		}
	
	@Override
	public Map<String, Object> selectAdoptList(int cp) {

		// 삭제 되지 않은 게시글 수 조회
		int listCount = mapper.getListCount();
		
		// Pagination 객체 생성
		// * Pagination 객체 : 게시글 목록 구성에 필요한 값을 저장
		Pagination pagination = new Pagination(cp, listCount);
		
		// 페이지 목록 조회
		
		/* ROWBOUND 객체 (MyBatis 제공 객체)
		 * 
		 * 지정된 크기(offset)만큼 건너뛰고
		 * 제한된 크기(limit) 만큼의 행을 조회하는 객체
		 * 
		 * */
		
		int limit = pagination.getLimit();
		int offset = (cp-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Adopt> adoptList = mapper.selectAdoptList(rowBounds);
		
		// 목록 조회결과 Pagination 객페를 Map으로 묶음
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("adoptList", adoptList);
		
		
		// 결과 반환
		return map;
	}
	
	// 게시글 상세 조회
	@Override
	public Adopt selectOneAdopt(Map<String, Integer> map) {
		
		
		
		return mapper.selectOneAdopt(map);
	}

	// 북마크 체크/ 해제
	@Override
	public int bookCheck(Map<String, Integer> map) {
		
		int result =0;
		
	   if(map.get("bookmark") == 1) { // bookmarkcheck 테이블에 delete
			
			result = mapper.deleteBookCheck(map);
	
		}
	   // 북마크가 해제된 상태인 경우 bookMark ==0
	   
	   else {
		   result = mapper.insertBookCheck(map);
	   }
		
		
		return result;
	}
	
	@Override
	public int adoptUpdate(Adopt adoptInput,MultipartFile thumnailImg,int statusCheck) {
	
			// 수정할 경로
			String updatePath = null;
			
			String rename = null;
			
			if(statusCheck==-1) {
				adoptInput.setThumnail("none");
			}
			
			// 업로드한 이미지가 있을 경우
			if(!thumnailImg.isEmpty()) {
				
				// updatePath 조합
				
				// 파일명 조합
				rename = Utility.fileRename(thumnailImg.getOriginalFilename());

				// /myPage/profile/변경된파일명.jpg
				updatePath = adoptWebPath+rename;
				
				adoptInput.setThumnail(updatePath);
			}
			
			
			int result = mapper.adoptUpdate(adoptInput);
			
			if(result > 0) {
				try {
					thumnailImg.transferTo(new File(adoptFolderPath + rename));
				} catch (Exception e) {
					e.printStackTrace();
					throw new AdoptInsertException("입양 게시글 이미지 삽입 중 예외 발생");
				}
			}
			
			
			return result;
			
	}
	
	/** 게시글 삭제
	 *
	 */
	@Override
	public int adoptDelete(Map<String, Integer> map) {
		return mapper.adoptDelete(map);
	}
	
	/** 작성자 이메일 찾기
	 *
	 */
	@Override
	public String writerEmail(int adoptNo) {
		return mapper.writerEmail(adoptNo);
	}
	
	/** 이메일에 작성할 동물 이름
	 *
	 */
	@Override
	public String adoptName(int adoptNo) {
		return mapper.adoptName(adoptNo);
	}
	
	@Override
	public String toEmail(int memberNo) {
		return mapper.toEmail(memberNo);
	}
	
	/** 이메일 전송
	 *
	 */
	@Override
	public int sendEmail(String htmlName, Adopt contactInput) {
		
		// 기입된 값 DB에 집어넣기
		int result = mapper.insertContact(contactInput);
		
		
		
		
		try {
			
			// 제목
			String subject =null; 
			
			switch(htmlName) {
			case "adoptionContactMail" :
				subject = "[PAWLIFE] 입양 문의 요청이 도착하였습니다.";
				break;
			}
			
			
			
			// 메일 보내는 객체
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			// 타임 리프
			MimeMessageHelper helper 
			= new MimeMessageHelper(mimeMessage, true, "UTF-8");
			
			
			
			helper.setTo(contactInput.getContactEmail()); // 받는 사람 이메일 지정
			helper.setSubject(subject); // 이메일 제목 지정
			
			helper.setText( loadHtml(htmlName, contactInput) , true);

			helper.addInline("logo", 
					new ClassPathResource("static/images/logo.png"));
		
			
			// 메일 보내기
			mailSender.send(mimeMessage);
			
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		
		return result;
	
	
	}
	
	
	
	// HTML 파일을 읽어와 String으로 변환 (타임리프 적용)
		public String loadHtml(String htmlName, Adopt contactInput) {
			
			// org.tyhmeleaf.Context 선택!!
			Context context = new Context();
			
			//타임리프가 적용된 HTML에서 사용할 값 추가
			context.setVariable("contactInput", contactInput);
			
			// templates/email 폴더에서 htmlName과 같은 
			// .html 파일 내용을 읽어와 String으로 변환
			return templateEngine.process("adoption/" + htmlName, context);
			
		}
	
		
		
		
		
		
		
		
		
	
	/** 검색 기능
	 *
	 */
	@Override
	public Map<String, Object> searchList(Map<String, Object> paramMap, int cp) {

		int listCount = mapper.getSearchCount(paramMap);
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit(); 
		int offset = (cp - 1) * limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Adopt> adoptList = mapper.selectSearchList(paramMap, rowBounds);
		
		// 4. 목록 조회 결과 + Pagination 객체를 Map으로 묶음
		Map<String, Object> map = new HashMap<>();
				
		map.put("pagination", pagination);
		map.put("adoptList", adoptList);
		
		// 5. 결과 반환
		return map;
		
				
		
		
	}

	
	
	/** 조회수 증가
	 *
	 */
	@Override
	public int updateReadCount(int adoptNo) {
		
		// 조회수 1 증가
		int result = mapper.updateReadCount(adoptNo);
		
		// 현재 조회 수 조회
		if(result>0) {
			return mapper.selectReadCount(adoptNo);
		}
		
		
		return 0;
	}
	
}
