package com.project.pawlife.myPage.model.service;

import java.util.List;

import com.project.pawlife.review.model.dto.Review;

public interface MyPageService {

	/** 내가 쓴 후기 게시글 조회
	 * @param memberNo 
	 * @return reviewList
	 */
	List<Review> selectReview(int memberNo);

}
