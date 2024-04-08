package com.lhj.book.main.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import com.lhj.book.main.model.dto.Book;

public interface MainService {




	/** 전체 조회
	 * @return
	 */
	List<Book> selectAll();

}
