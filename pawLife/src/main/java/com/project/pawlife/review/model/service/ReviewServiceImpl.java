package com.project.pawlife.review.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.common.util.Pagination;
import com.project.pawlife.common.util.Utility;
import com.project.pawlife.review.model.dto.Review;
import com.project.pawlife.review.model.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional

public class ReviewServiceImpl implements ReviewService{

	private final ReviewMapper mapper;

	
	@Value("${my.review.web-path}")
	private String reviewWebPath;

	@Value("${my.review.folder-path}")
	private String reviewFolderPath;
	
	// 후기 게시글 작성
	@Override
	public int reviewInsert(Review inputReview, MultipartFile thumnailImg, int memberNo) {
	
		String updatePath = null;
		String rename = null;
		
		if( !thumnailImg.isEmpty()) {
			rename = Utility.fileRename(thumnailImg.getOriginalFilename());
			updatePath = reviewWebPath + rename;
			
			inputReview.setThumnail(updatePath);
			inputReview.setMemberNo(memberNo);
		}
		
		int result = mapper.reviewInsert(inputReview);
		
		if(result < 0) {
			try {
				thumnailImg.transferTo(new File(reviewFolderPath + rename));
			} catch (Exception e) {
			
			}
		}
		
		return result;
	}



	// 후기 게시판 리스트
	@Override
	public Map<String, Object> selectReviewList(int cp) {
		
		int listCount = mapper.getListCount();
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Review> reviewList = mapper.selectReviewList(rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("reviewList", reviewList);
		
		return map;
	}


	// 게시글 상세 조회
	@Override
	public Review selectOneReview(Map<String, Integer> map) {
		
		return mapper.selectOneReview(map);
	}


	// 게시글 수정
	@Override
	public int reviewUpdate(Review inputReview, MultipartFile thumnailImg, int statusCheck) {

		String updatePath = "";
		String rename = "";
		
		if(statusCheck == -1) {
			inputReview.setThumnail("none");
		}
		
		// 업로드 한 이미지가 있을 경우
		if( !thumnailImg.isEmpty()) { 
			
			rename = Utility.fileRename(thumnailImg.getOriginalFilename());
			updatePath = reviewWebPath + rename;
			inputReview.setThumnail(updatePath);
		}
		
		int result = mapper.reviewUpdate(inputReview);
		
		if(result > 0) {
			try {
				thumnailImg.transferTo(new File(reviewFolderPath + rename));
			} catch (Exception e) {
//				e.printStackTrace();
//				throw new ReviewInsertException("후기 게시글 이미지 삽입 중 예외 발생");
			}
		}
		return result;
	}


	// 게시글 삭제
	@Override
	public int reviewDelete(Map<String, Integer> map) {
		
		return mapper.reviewDelete(map);
	}



	
}
