package com.lhj.book.management.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lhj.book.management.model.dto.Book;
import com.lhj.book.management.model.service.ManagementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("manage")
@RequiredArgsConstructor
public class ManagementController {
	
	
	private final ManagementService service;
	
	/** 책 전체 조회
	 * @return bookList
	 */
	@ResponseBody
	@GetMapping("selectAll")
	public List<Book> book(){
		return service.selectAll();
	}
	
	
	
	/** 책 등록 페이지 이동
	 * @return
	 */
	@GetMapping("insert")
	public String bookInsert() {
		return "manage/insert";
	}
	
	
	/** 책 등록 
	 * @return
	 */
	@PostMapping("insert")
	public String bookInsert(
		Book inputBook, RedirectAttributes ra) {
		
		int result = service.bookInsert(inputBook);
		
		String message = null;
		String path = null;
		
		if(result > 0) {
			message = "등록 성공!";
			path = "/";
		}else {
			message = "등록 실패...";
			path = "insert";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	
	/** 책 검색 페이지 이동
	 * @return
	 */
	@GetMapping("search")
	public String search() {
		return "manage/search";
	}
	
	
	/** 책 검색
	 * @return bookList
	 */
	@ResponseBody
	@GetMapping("searchList")
	public List<Book> bookSearch(
		@RequestParam("inputTitle") String inputTitle){
		return service.bookSearch(inputTitle);
	}
	
	
	/** 책 가격 수정
	 * @param book
	 * @return result
	 */
	@ResponseBody
	@PutMapping("updatePrice")
	public int bookUpdatePrice(@RequestBody Book book) {
		return service.bookUpdatePrice(book);
	}
	
	
	
	/** 책 삭제
	 * @param bookNo
	 * @return result
	 */
	@ResponseBody
	@DeleteMapping("delete")
	public int bookDelete(@RequestBody int bookNo) {
		return service.bookDelete(bookNo);
	}
	
	
	
	
}