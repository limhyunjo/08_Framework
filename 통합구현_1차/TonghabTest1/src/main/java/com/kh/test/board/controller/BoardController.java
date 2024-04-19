package com.kh.test.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.kh.test.board.model.dto.Board;
import com.kh.test.board.model.service.BoardService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

	 private final BoardService service;
		
	
	@GetMapping("main")
	public String ajaxMain( ) {
		return "ajax/main";
	}
	
	
	@GetMapping("selectList")
	public String selectBoard( @RequestParam ("boardTitle")String boardTitle, Model model ) {
		
	
		List<Board> selectList = service.selectList(boardTitle);
		
		String path = null;
		
		
		if(selectList.isEmpty()) { 
			path ="searchFail"; 
			
				
		}else {
			path = "searchSuccess";  
			
			model.addAttribute("selectList",selectList) ;
		
		}
		
		return path;
	}

	
	
}
