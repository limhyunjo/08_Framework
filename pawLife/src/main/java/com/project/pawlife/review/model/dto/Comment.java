package com.project.pawlife.review.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Comment {

   // COMMENT 테이블 컬럼 추가
   private int commentNo;
   private String commentContent;
   private String commentWriteDate;
   private String commentDelFl;
   
	private int reviewNo;
	private int memberNo;

	
	// 댓글 조회시 회원 프로필,닉네임
	private String memberNickname;
	private String profileImg;
	   
}
