package com.lhj.book.main.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lhj.book.main.model.dto.Book;
import com.lhj.book.main.model.service.MainService;

import lombok.extern.slf4j.Slf4j;

@Controller 
@Slf4j
public class MainController {

	@Autowired
	private MainService service;
	
	@RequestMapping("/") // "/" 요청 매핑 (method 가리지 않음)
	public String mainPage() {
		
		
		return "common/main"; // common의 main 파일로 forward한다
	}
	


	@ResponseBody
	@GetMapping("book/bookList") 
	public List<Book> selectAll(	) {
	
	
		List<Book>bookList =service.selectAll();
		
		return bookList; // common의 main 파일로 forward한다
	}
}