package com.project.pawlife.review.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.review.model.dto.Review;

/**
 * 
 */
public interface ReviewService {

	// 게시글 작성
	int reviewInsert(Review inputReview, MultipartFile thumnailImg, int memberNo);

	/** 게시글 리스트
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectReviewList(int cp);

	/** 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Review selectOneReview(Map<String, Integer> map);

	/** 게시글 수정
	 * @param inputReview
	 * @param thumnailImg
	 * @param statusCheck
	 * @return
	 */
	int reviewUpdate(Review inputReview, MultipartFile thumnailImg, int statusCheck);

	/** 게시글 삭제
	 * @param map
	 * @return
	 */
	int reviewDelete(Map<String, Integer> map);

	/** 게시글 검색
	 * @param paramMap
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectReviewList(Map<String, Object> paramMap, int cp);

	/** 조회수 증가
	 * @param reviewNo
	 * @return
	 */
	int updateReadCount(int reviewNo);




}
