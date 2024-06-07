package com.project.foodpin.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.foodpin.member.model.dto.Member;
import com.project.foodpin.store.model.dto.Store;
import com.project.foodpin.store.model.service.SearchStoreService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("store")
public class SearchStoreController {

	private  final SearchStoreService service;

	// 가게 검색 페이지 이동(+ 카테고리 선택된 상태로 검색되어야 함)
	/*
	 * @GetMapping("storeSearch") public String searchPage() {
	 * 
	 * return "store/storeSearch"; }
	 */

	
	  @GetMapping("storeSearch/{categoryCode}") 
	  public String searchStoreList(@PathVariable("categoryCode") int categoryCode,  
	  Store storeNo,	  
	  @SessionAttribute(value = "loginMember", required = false) Member loginMember, 
	  Model model, 
	  RedirectAttributes ra) {
	  
		
			
			

			Map<String, Object> map = new HashMap<>();
			
			if(loginMember != null) {
				int memberNo = loginMember.getMemberNo();
				map.put("memberNo", memberNo);
			}
		
			map.put("storeNo", storeNo);
			map.put("categoryCode", categoryCode);
			
		
			Store store = service.storeSearchDetail(map);
			
			String storeLocation = store.getStoreLocation();
			String[] arr = storeLocation.split("\\^\\^\\^");



			model.addAttribute("postcode", arr[0]);
			model.addAttribute("address", arr[1]);
			model.addAttribute("detailAddress", arr[2]);

			

			String path = null;

			model.addAttribute("store", store);
			// 위까지 가게 상세 조회
			
			
			// 동기로 메인에서 카테고리로 선택된 가게 리스트
			// 검색 X key 값이 null인 경우 가게 리스트 조회
			/*
			 * model.addAttribute("categoryStoreList",categoryStoreList);
			 * model.addAttribute("searchCategoryList",searchCategoryList);
			 */
			//검색으로 조회된 경우 (key값 있는 경우)
			
			//

			path = "/store/storeSearch";

			
			return path;
	  }	
}
