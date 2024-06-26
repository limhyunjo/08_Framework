package com.project.foodpin.myPage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.foodpin.member.model.dto.Member;
import com.project.foodpin.myPage.model.service.ManagerMyPageService;
import com.project.foodpin.reservation.model.dto.Reservation;
import com.project.foodpin.review.model.dto.Report;
import com.project.foodpin.store.model.dto.Request;
import com.project.foodpin.store.model.dto.Store;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("myPage/manager")
public class ManagerMyPageController {

	private final ManagerMyPageService service;
	
	// 가게 입점 내역 조회
	@GetMapping("storeEnroll")
	public String managerEnroll(
		Model model) {
		
		int memberCode = 2;
		String memberStatus = "W";
		List<Member> storeMember = service.storeRequestList(memberCode, memberStatus);
		model.addAttribute("storeMember", storeMember);
		
		for (Member stores : storeMember) {
	        String storeLocation = stores.getStoreLocation();
	        String arr = storeLocation.replace("^^^", " ");
	        int firstSpaceIndex = arr.indexOf(" ");
	        String addressWithoutPostcode = arr.substring(firstSpaceIndex + 1);
	        stores.setStoreLocation(addressWithoutPostcode);
	        
	        String memberTel = stores.getMemberTel();
	        String formattedTel = memberTel.substring(0, 3) + "-" + memberTel.substring(3, 7) + "-" + memberTel.substring(7);
	        stores.setMemberTel(formattedTel);
	        
	        String storeNo = stores.getStoreNo();
	        String formattedStoreNo = storeNo.substring(0, 3) + "-" + storeNo.substring(3, 5) + "-" + storeNo.substring(5);
	        stores.setStoreNo(formattedStoreNo);
	    }	
		
		return "myPage/manager/storeEnroll";
	}
	
	
	// 가게 승인
	@PostMapping("approveMember/{memberNo}")
	public ResponseEntity<Map<String, Object>> approveMember(
		@PathVariable("memberNo") int memberNo) {
		
		boolean approve = service.approveMember(memberNo);
		Map<String, Object> response = new HashMap<>();
		response.put("success", approve);
		return ResponseEntity.ok(response);
	}
	
	// 가게 거절
	@PostMapping("refuseMember/{memberNo}")
	public ResponseEntity<Map<String, Object>> refuseMember(
			@PathVariable("memberNo") int memberNo) {
		
		boolean refuse = service.refuseMember(memberNo);
		Map<String, Object> response = new HashMap<>();
		response.put("success", refuse);
		return ResponseEntity.ok(response);
	}
	
	
	// 가게 입점 가게 리스트
	@GetMapping("ableStore")
	public String ableStore(
		Model model) {
			
		int memberCode = 2;
		String memberStatus = "N";
		List<Member> storeMember = service.storeRequestList(memberCode, memberStatus);
		for (Member stores : storeMember) {
	        String storeLocation = stores.getStoreLocation();
	        String arr = storeLocation.replace("^^^", " ");
	        int firstSpaceIndex = arr.indexOf(" ");
	        String addressWithoutPostcode = arr.substring(firstSpaceIndex + 1);
	        stores.setStoreLocation(addressWithoutPostcode);
	        
	        String memberTel = stores.getMemberTel();
	        String formattedTel = memberTel.substring(0, 3) + "-" + memberTel.substring(3, 7) + "-" + memberTel.substring(7);
	        stores.setMemberTel(formattedTel);
	        
//	        String storeNo = stores.getStoreNo();
//	        String formattedStoreNo = storeNo.substring(0, 3) + "-" + storeNo.substring(3, 5) + "-" + storeNo.substring(5);
//	        stores.setStoreNo(formattedStoreNo);
	    }	
		
		model.addAttribute("storeMember", storeMember);
		return "myPage/manager/ableStore";
	}
	
	// 가게 거절 가게 리스트
	@GetMapping("unableStore")
	public String unableStore(
			Model model) {
		
		int memberCode = 2;
		String memberStatus = "Y";
		List<Member> storeMember = service.storeRequestList(memberCode, memberStatus);
		for (Member stores : storeMember) {
	        String storeLocation = stores.getStoreLocation();
	        String arr = storeLocation.replace("^^^", " ");
	        int firstSpaceIndex = arr.indexOf(" ");
	        String addressWithoutPostcode = arr.substring(firstSpaceIndex + 1);
	        stores.setStoreLocation(addressWithoutPostcode);
	        
	        String memberTel = stores.getMemberTel();
	        String formattedTel = memberTel.substring(0, 3) + "-" + memberTel.substring(3, 7) + "-" + memberTel.substring(7);
	        stores.setMemberTel(formattedTel);
	        
	        String storeNo = stores.getStoreNo();
	        String formattedStoreNo = storeNo.substring(0, 3) + "-" + storeNo.substring(3, 5) + "-" + storeNo.substring(5);
	        stores.setStoreNo(formattedStoreNo);
	    }	
		model.addAttribute("storeMember", storeMember);
		return "myPage/manager/unableStore";
	}
	
	// 가게 승인
	@PostMapping("unableStore/{memberNo}")
	public ResponseEntity<Map<String, Object>> ableStore(
		@PathVariable("memberNo") int memberNo) {
		
		boolean approve = service.approveMember(memberNo);
		Map<String, Object> response = new HashMap<>();
		response.put("success", approve);
		return ResponseEntity.ok(response);
	}
	
	// 가게 폐점
	@PostMapping("closeStore/{memberNo}")
	public ResponseEntity<Map<String, Object>> closeStore(
			@PathVariable("memberNo") int memberNo) {
		
		boolean closeStore = service.closeStore(memberNo);
		Map<String, Object> response = new HashMap<>();
		response.put("success", closeStore);
		return ResponseEntity.ok(response);
	}
	
	// 리뷰 신고 조회
	@GetMapping("reportReview")
	public String storeInfo(Model model) {
		
		List<Report> reportList = service.reportList();
		int reportCount = service.reportCount();
		
		model.addAttribute("reportCount", reportCount);
		model.addAttribute("reportList", reportList);
		
		List<Report> completeReportList = service.completeReportList();
		int completeReportCount = service.completeReportCount();
		
		model.addAttribute("completeReportCount", completeReportCount);
		model.addAttribute("completeReportList", completeReportList);
		
		return "myPage/manager/reportReview";
	}
	
	// 신고 리뷰 삭제 처리
	@PostMapping("deleteReport/{reportNo}")
	public ResponseEntity<Map<String, Object>> deleteReport(
		@PathVariable("reportNo") int reportNo) {
		
		boolean deleteReport = service.deleteReport(reportNo);
		Map<String, Object> response = new HashMap<>();
		response.put("success", deleteReport);
		return ResponseEntity.ok(response);
	}
	
	// 신고 리뷰 불충분 처리
	@PostMapping("notReportReview/{reportNo}")
	public ResponseEntity<Map<String, Object>> notReportReview(
		@PathVariable("reportNo") int reportNo) {
		
		boolean notReportReview = service.notReportReview(reportNo);
		Map<String, Object> response = new HashMap<>();
		response.put("success", notReportReview);
		return ResponseEntity.ok(response);
	}
	
	
	// 정보 정정 신청 조회
	@GetMapping("managerStoreInfo")
	public String memberReportReview(Model model) {
		
		List<Request> infoRequest = service.infoRequestList();
		int reportInfoCount = service.reportInfoCount();
		
		model.addAttribute("infoRequest", infoRequest);
		model.addAttribute("reportInfoCount", reportInfoCount);
		
		return "myPage/manager/managerStoreInfo";
	}
	
	// 정보 정정 처리 완료
	@PostMapping("managerStoreInfo/{requestNo}")
	public ResponseEntity<Map<String, Object>> completeRequest(
		@PathVariable("requestNo") int requestNo) {
		
		boolean completeRequest = service.completeRequest(requestNo);
		Map<String, Object> response = new HashMap<>();
		response.put("success", completeRequest);
		return ResponseEntity.ok(response);
	}
	
	
	
}
