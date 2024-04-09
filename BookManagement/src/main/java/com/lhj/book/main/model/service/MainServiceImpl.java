package com.lhj.book.main.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.lhj.book.main.model.dto.Book;
import com.lhj.book.main.model.mapper.MainMapper;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class MainServiceImpl implements MainService {

	@Autowired
	private MainMapper mapper;
	
@Override
public List<Book> selectAll() {

	return mapper.selectAll();
}


@Override
public int addBook(String bookTitle, String bookWriter, int bookPrice) {
	
	Book book = new Book();
	book.setBookTitle(bookTitle);
	book.setBookWriter(bookWriter);
	book.setBookPrice(bookPrice);
	
	
	return mapper.addBook(bookTitle, bookWriter, bookPrice);
}
}
