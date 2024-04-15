package com.kh.test.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.test.customer.model.service.CustomerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Controller
public class CustomerController {

	@Autowired
	 private CustomerService service;
	
	
	@RequestMapping("/") // "/" 요청 매핑 (method 가리지 않음)
	public String mainPage() {
		
		
		return "static/index"; 
	}
	
	@PostMapping("result") //  "/todo/add" POST 방식 요청 매핑
	public String addCustomer(
			@RequestParam("customerName") String customerName,
			@RequestParam("customerTel")String customerTel,
			@RequestParam("customerAddress")String customerAddress,
			Model model
			) {
		
		String path =  null;
		
		int result = service.addCustomer(customerName, customerTel,customerAddress);
		
		if(result>0) {
			
			model.addAttribute("customerName", customerName);
			path="result";
		}
			
		return path;
	}
	
}
	