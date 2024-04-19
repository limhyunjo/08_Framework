package com.kh.test.board.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.test.board.model.dto.Board;

@Mapper
public interface BoardMapper {

	/** 게시글 조회
	 * @param boardTitle
	 * @return
	 */
	List<Board> selectList(String boardTitle);
	


}
