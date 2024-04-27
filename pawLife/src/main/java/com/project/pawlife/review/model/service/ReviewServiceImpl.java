package com.project.pawlife.review.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.pawlife.review.model.dto.Review;
import com.project.pawlife.review.model.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional

public class ReviewServiceImpl implements ReviewService{

	private final ReviewMapper mapper;

	
	
	// 후기 게시글 작성
	@Override
	public int reviewWrite(Review inputReivew) { return mapper.reviewWrite(inputReivew); }

	
}
