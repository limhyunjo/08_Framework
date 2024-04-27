package com.project.pawlife.review.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.pawlife.review.model.dto.Review;

@Mapper
public interface ReviewMapper {

	// 게시글 작성
	int reviewWrite(Review inputReivew);

}
