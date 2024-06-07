package com.project.foodpin.main.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.foodpin.main.model.service.MainService;
import com.project.foodpin.store.model.dto.Store;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MainController {

	private final MainService service;

	@RequestMapping("/")
	public String main() {
		return "common/main";
	}

	@GetMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		ra.addFlashAttribute("message", "로그인 후 이용해 주세요");
		return "redirect:/";
	}


	/*
	 * // 가게 카테고리 조회
	 * 
	 * @GetMapping("/{categoryCode:[0-9]+}") public String mainCategoryList(
	 * 
	 * @PathVariable("categoryCode") int categoryCode, Model mode) {
	 * 
	 * 
	 * log.debug("categoryCode: " + categoryCode);
	 * 
	 * 
	 * // 조회 서비스 호출 후 결과 반환 Map<String, Object> map = null;
	 * 
	 * // 검색이 아닌 경우 if(paramMap.get("key") == null) { map =
	 * service.selectBoardList(categoryCode);
	 * 
	 * } else { // 검색인 경우
	 * 
	 * // boardCode를 paramMap에 추가 paramMap.put("boardCode", boardCode);
	 * 
	 * // 검색 서비스 호출 // map으로 반환 받아 돌아올 것임 map = service.searchList(paramMap, cp);
	 * 
	 * }
	 * 
	 * // 몇번 게시판 몇 페이지를 조회할 것인지 알려주기 위해 boardCode, cp 보냄 // boardCode : 게시판 번호 // cp
	 * 현재 페이지 // -> 어느 게시판의 몇번 페이지인지 알기 위해 보냄
	 * 
	 * // 동작 실행이 안되면 a태그가 잘못되었거나 pathVariable이 잘못 써짐
	 * 
	 * // Map으로 묶은 이유는 메서드 반환을 하나만 할 수 있어서 하나로 묶어서 보내고 // 사용할 때는 다시 쪼갬
	 * 
	 * // pagination이랑 boardList를 담아서 전달함
	 * 
	 * model.addAttribute("pagination",map.get("pagination"));
	 * model.addAttribute("boardList",map.get("boardList"));
	 * 
	 * // 주소에 쿼리스트링으로 치고 디버그로 확인 http://localhost/board/1?cp=1
	 * 
	 * return "board/boardList"; //boardList.html로 forward }
	 */
	
	@GetMapping("/")
	public String mainDisplay(Model model, HttpServletRequest req, HttpServletResponse resp) {

		// 가게 게시글 조회
		List<Store> storeList = service.selectMainStore();

		if (!storeList.isEmpty()) {
			model.addAttribute(storeList);
		}

		return "common/main";
	}

}
