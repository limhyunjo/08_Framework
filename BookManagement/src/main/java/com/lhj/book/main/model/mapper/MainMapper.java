package com.lhj.book.main.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lhj.book.main.model.dto.Book;

@Mapper
public interface MainMapper {



	/**
	 * @return
	 */
	List<Book> selectAll();



}
