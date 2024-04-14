package com.lhj.book.management.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.lhj.book.management.model.dto.Book;

@Mapper
public interface ManagementMapper {



	/** 전체 조회
	 * @return
	 */
	List<Book> selectAll();



	/** 책 추가
	 * @param bookTitle
	 * @param bookWriter
	 * @param bookPrice
	 * @return
	 */
	int addBook(String bookTitle, String bookWriter, int bookPrice);



}
