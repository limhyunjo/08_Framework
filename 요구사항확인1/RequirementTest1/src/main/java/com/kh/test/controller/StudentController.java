package com.kh.test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.test.model.dto.Student;
import com.kh.test.model.service.StudentService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StudentController {

	private final StudentService service;
	
	@GetMapping("index")
	public String ajaxMain( ) {
		return "index";
	}
	
	
	@ResponseBody
	@GetMapping("selectStudentList")
	public List<Student> selectStudentList() {

		List<Student> studentList = service.selectAll();
		
		log.debug(studentList.toString());
		
		return studentList;

	}
	
}
