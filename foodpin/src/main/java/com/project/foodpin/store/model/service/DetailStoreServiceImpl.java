package com.project.foodpin.store.model.service;


import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.foodpin.myPage.model.dto.Off;
import com.project.foodpin.review.model.dto.Hash;
import com.project.foodpin.review.model.dto.Review;
import com.project.foodpin.review.model.dto.ReviewHash;
import com.project.foodpin.store.model.dto.Store;
import com.project.foodpin.store.model.mapper.DetailStoreMapper;

import kotlin.jvm.Throws;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
@RequiredArgsConstructor
public class DetailStoreServiceImpl implements DetailStoreService{

	private final DetailStoreMapper mapper;

	// 가게 상세 조회
	@Override
	public Store storeDetail(Map<String, Object> map) {
	
		return mapper.storeDetail(map);
	}
	
	


	
	// 가게 리뷰 상세 조회
	@Override
	public List<Review> reviewDetail(String storeNo) {
		
		List<Review> reviewList = mapper.reviewDetail(storeNo);
		
		return reviewList;
	}
	

	


	// 가게 찜 
	@Override
	public int storeLike(Map<String, Object> map) {
		
      int result = 0;
		
		//1. 좋아요가 체크된 상태인 경우 (bookmark ==1)
		// -> Bookmark 테이블에 DELETE
		if(Integer.parseInt(String.valueOf(map.get("bookmark")) )== 1) {
			
			result = mapper.deleteStoreLike(map);
			
			
			
		}
			
		//2. 좋아요가 해제된 상태인 경우 (bookmark ==0)
		// -> Bookmark 테이블에 INSERT
		
		else {
			
			result = mapper.insertStoreLike(map);
		}
		
		// 3. 다시 해당 게시글의 좋아요 개수를 조회해서 반환
		if(result > 0) {
			return mapper.selectLikeCount(map.get("storeNo"));
		}
		
		
		
		return -1;
	}


	// 리뷰 신고
	@Override
	public int reviewReport(Map<String, Object> map) {
		
		int reviewNo = Integer.parseInt(String.valueOf(map.get("reviewNo")));
		
		int memberNo = mapper.selectMemberNo(reviewNo);
		
		map.put("memberNo", memberNo);
		
		return mapper.reviewReport(map);
	}




	// 가게 정보 정정 신고
	@Override
	public int storeReport(Map<String, Object> map) {
	    // Map에서 데이터를 추출
	    String storeNo = (String) map.get("storeNo");
	    String requestContent = (String) map.get("requestContent");
	    String requestCategoryTitle = (String) map.get("requestCategoryTitle");


	    // Map에 requestCategoryCode를 추가
	    switch (requestCategoryTitle) {
	        case "changeBasicinfo":
	            map.put("requestCategoryCode", "1");
	            break;
	        case "changeMenu":
	            map.put("requestCategoryCode", "2");
	            break;
	        case "chageStoreTime":
	            map.put("requestCategoryCode", "6");
	            break;
	        case "storeClosed":
	            map.put("requestCategoryCode", "7");
	            break;
	        default:
	            map.put("requestCategoryCode", "0"); // 기본값
	            break;
	    }

	   
	    return mapper.storeReport(map);
	}

	// 가게 해시태그 검색
	@Override
	public List<Store> hashSearchStore(String hashNo) {
		return mapper.hashSearchStore(hashNo);
	}

	// 가게 해시태그 검색(해시태그 내용)
	@Override
	public Hash hashTitle(String hashNo) {
		return mapper.hashTitle(hashNo);
	}

	// 가게 해시태그 추가
	@Override
	public List<Store> addHash(List<Hash> hashList) {
		
		List<Hash> hashNoList = new ArrayList<>();
		for(int i = 0; i < hashList.size(); i++) {
			if(!hashList.isEmpty()) {
				
				Hash hash = Hash.builder()
						.hashNo(hashList.get(i).getHashNo())
						.build();
				hashNoList.add(hash);
			}
		}
		if(hashList.isEmpty()) return null;
		
		
		return mapper.addHash(hashNoList);
	}
	
	// 해시태그 추가(해시태그 타이틀
	@Override
	public List<Hash> hashTitle(List<Hash> hashList) {
		List<Hash> hashNoList = new ArrayList<>();
		for(int i = 0; i < hashList.size(); i++) {
			if(!hashList.isEmpty()) {
				
				Hash hash = Hash.builder()
						.hashNo(hashList.get(i).getHashNo())
						.build();
				hashNoList.add(hash);
			}
		}
		if(hashList.isEmpty()) return null;
		
		
		return mapper.hashTitleList(hashNoList);
	}


	// 가게 영업시간, 휴무일, 브레이크타임 조회
	@Override
	public Map<String, Object> storeOpen(String storeNo) {
		
		String offWeek = "";
		
		// 영업시간, 브레이크타임 조회
		Store openBreak = mapper.storeOpen(storeNo); 
		
		// 고정 휴무일 조회
		List<String> offWeekList = mapper.selectWeekOff(storeNo); 
		
		for (String off : offWeekList) { // 고정 휴무(요일) 구분자 넣어서 String 형태로 전달 
			offWeek += off + "/";
		}
		
//        LocalDate today = LocalDate.now(); // 오늘 날짜
//        LocalDate lastDay = today.plus(7, ChronoUnit.DAYS);
//        
//		List<Off> offDay = mapper.selecDayOff(storeNo); // 고정 휴무일 조회
		
		Map<String, Object> map = new HashMap<>();
		map.put("openBreak", openBreak);
		map.put("offWeek", offWeek);
		
		return map;
	}






	

	
	

	
}
