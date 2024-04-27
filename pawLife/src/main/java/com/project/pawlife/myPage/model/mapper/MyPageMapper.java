package com.project.pawlife.myPage.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.pawlife.review.model.dto.Review;

@Mapper
public interface MyPageMapper {

	/** 로그인한 회원이 작성한 게시글 조회
	 * @return
	 */
	List<Review> selectView(int memberNo);

}
