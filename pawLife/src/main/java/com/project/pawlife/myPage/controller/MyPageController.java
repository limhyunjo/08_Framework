package com.project.pawlife.myPage.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.pawlife.myPage.model.service.MyPageService;

import lombok.RequiredArgsConstructor;

//@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MyPageController {

	
	private final MyPageService service;


	
	@GetMapping("first")
	public String first() {
		return "myPage/myPage-first";
		
		
	}
	
	
	
   @GetMapping("myPage-profileupdate")
   public String profileUpdate() {
      
      return "myPage/myPage-profileupdate";
   }
   
   
   @GetMapping("myPage-myComment")
   public ResponseEntity<String> getMyCommentPage() {
       try {
           // ClassPathResource를 사용하여 HTML 파일을 가져옴
           ClassPathResource htmlFile = new ClassPathResource("myPage/myPage-myComment");
           
           // HTML 파일을 InputStream으로 읽어들임
           InputStream inputStream = htmlFile.getInputStream();
           
           // InputStream에서 HTML 내용을 문자열로 변환
           byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
           String htmlContent = new String(bdata, StandardCharsets.UTF_8);

           // HTML 내용을 클라이언트에 반환
           return ResponseEntity.ok(htmlContent);
       } catch (IOException e) {
           // 파일을 찾을 수 없거나 읽을 수 없는 경우 404 에러 반환
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HTML file not found");
       }
   }
}
