package com.lhj.book.management.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhj.book.management.model.dto.Book;

import lombok.RequiredArgsConstructor;

@Transactional
@Service
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService {

	private final ManagementService mapper;
	
	// 책 전체 조회
	@Override
	public List<Book> selectAll() {
		return mapper.selectAll();
	}
	
	// 책 등록
	@Override
	public int bookInsert(Book inputBook) {
		return mapper.bookInsert(inputBook);
	}
	
	// 책 검색
	@Override
	public List<Book> bookSearch(String inputTitle) {
		return mapper.bookSearch(inputTitle);
	}
	
	// 책 가격 수정
	@Override
	public int bookUpdatePrice(Book book) {
		return mapper.bookUpdatePrice(book);
	}

	
	// 책 삭제
	@Override
	public int bookDelete(int bookNo) {
		return mapper.bookDelete(bookNo);
	}
	
}
