package com.project.foodpin.myPage.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.foodpin.common.util.Utility;
import com.project.foodpin.member.model.dto.Member;
import com.project.foodpin.myPage.model.dto.Off;
import com.project.foodpin.myPage.model.mapper.StoreMyPageMapper;
import com.project.foodpin.reservation.model.dto.Reservation;
import com.project.foodpin.review.model.dto.Review;
import com.project.foodpin.review.model.dto.ReviewReply;
import com.project.foodpin.store.model.dto.Menu;
import com.project.foodpin.store.model.dto.Store;
import com.project.foodpin.store.model.dto.StoreCategory;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class StoreMyPageServiceImpl implements StoreMyPageService{
	
	/* 이미지 패스 */
	@Value("${my.store.web-path}") // 가게 썸네일
	private String storeWebPath;
	
	@Value("${my.store.folder-path}")
	private String storeFolderPath;
	
	@Value("${my.menu.web-path}") // 메뉴
	private String menuWebPath;
	
	@Value("${my.menu.folder-path}")
	private String menuFolderPath;

	/* 매퍼 */
	private final StoreMyPageMapper mapper;
	private final BCryptPasswordEncoder bcrypt;

	
	
	
	// 가게 정보 수정 화면 이동
	@Override
	public Store selectstoreInfo(int memberNo) {
		return mapper.selectstoreInfo(memberNo);
	}
	
	// 가게 정보 조회
	@Override
	public Store selectstoreInfoJs(String storeNo) {
		return mapper.selectstoreInfoJs(storeNo);
	}
	
	// 모든 카테고리 조회
	@Override
	public List<StoreCategory> selectCategoryAll() {
		
		return mapper.selectCategoryAll();
	}
	
	// 가게 카테고리 조회
	@Override
	public List<StoreCategory> selectCategory(String storeNo) {
		
		return mapper.selectCategory(storeNo);
	}
	
	// 가게 정보 수정
	@Override
	public int storeInfoUpdate(Store inputStore) {
		
		String updatePath = "";
		String rename = "";
		String storeNo = inputStore.getStoreNo();
		

	    // 이미지 업로드 처리
	    if (inputStore.getImgStatus() == 1) { 
	        rename = Utility.fileRename(inputStore.getStoreImgInput().getOriginalFilename());
	        updatePath = storeWebPath + rename;
	    } else if (inputStore.getImgStatus() == -1) {
	        // 새로운 이미지가 업로드되지 않았고 상태가 -1인 경우 기존 이미지 경로 유지
	        updatePath = mapper.selectStoreImg(storeNo);
	    }

	    // 가게 객체에 이미지 경로 설정
	    inputStore.setStoreImg(updatePath);

	    // 가게 정보 업데이트
	    int result = mapper.storeInfoUpdate(inputStore);
		
		// 변경된 이미지가 있는 경우만 이미지 파일 저장
		if(result > 0 && inputStore.getImgStatus() == 1) {
			try {
				inputStore.getStoreImgInput().transferTo(new File(storeFolderPath + rename));
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		} 
		
		// 카테고리 변경
		String[] inputCategorys = inputStore.getCategorys().split("/");
		
		Map<String, Object> categoryMap = new HashMap<>();
		categoryMap.put("storeNo", inputStore.getStoreNo());
		
		List<StoreCategory> storeCtg = mapper.selectCategory(inputStore.getStoreNo());		
		
		// 기존 등록된 카테고리가 있는 경우 카테고리 삭제
		if(!storeCtg.isEmpty()) result = mapper.categoryDelete(inputStore.getStoreNo()); 
		
		if(result > 0) { // 변경된 카테고리 데이터 등록
	        for (String ctg : inputCategorys) {
	            int categoryCode = Integer.parseInt(ctg);
	            categoryMap.put("categoryCode", categoryCode);
	            categoryMap.put("storeNo", inputStore.getStoreNo());
	            
				mapper.categoryUpdate(categoryMap); 
	        }
		}
		return result;
	}
	
	// ------ 메뉴 ------

	// 메뉴 조회
	@Override
	public List<Menu> menuSelect(String storeNo) {
		
		return mapper.menuSelect(storeNo);
	}

	// 메뉴 수정 (삭제, 추가)
	@Override
	public int menuUpdate(List<Menu> inputMenuList, List<MultipartFile> imgUrlList) {
		
		int result = 0;
		String updatePath = "";
		String rename = "";
		
		result = mapper.deleteAllMenu(inputMenuList.get(0).getStoreNo()); // 기존 메뉴 데이터 삭제 (플래그 변경)
		
		// 메뉴명이 비어있지 않은 경우 (입력된 데이터가 있는 경우)
		if( !inputMenuList.get(0).getMenuTitle().isEmpty()) { 
			
			for (Menu menu : inputMenuList) {

				// 업로드 한 메뉴 이미지가 있는 경우
				if( !imgUrlList.isEmpty()) { 
					
					for(MultipartFile imgUrl : imgUrlList) {
						
						rename = Utility.fileRename(imgUrl.getOriginalFilename());
						updatePath = menuWebPath + rename;
						menu.setMenuImgUrl(updatePath);
					}
				}
				
				result = mapper.insertMenu(menu); // 메뉴 등록
				
				if(result > 0) { // db등록 성공시 파일 업로드 폴더에 이미지 저장
					
					try {
						menu.getMenuImg().transferTo(new File(menuFolderPath + rename)); // db등록 성공시 파일 업로드()
					}catch (Exception e) {
						e.printStackTrace();
					}
				} // if
			}
			
		}
		return result;
	}


	// ------ 휴무일 ------
	
	// 고정 휴무일 변경
	@Override
	public int updateOffWeek(List<Off> offList) {
		
		int result = 0;
		String StoreNo = offList.get(0).getStoreNo(); // StoreNo 꺼내오기
		
		int count = mapper.countOffWeek(StoreNo); // 기존 저장된 데이터 있는지 조회
		
		// 존재하는 경우 기존 데이터 삭제
		if(count > 0)  result = mapper.deleteOffWeek(StoreNo);
		
		// 고정 휴무일이 변경되는 경우 (완전 삭제되는 경우에는 수행X)
		if( !offList.get(0).getOffWeek().isEmpty()) {
			
			for(Off off : offList) {
				result = mapper.insertOffWeek(off);
			}
		}
		
		return result;
	}
	
	// 고정 휴무일 조회
	@Override
	public List<Off> selectWeekOff(String storeNo) {

		return mapper.selectWeekOff(storeNo);
	}
	
	// 지정 휴무일 조회
	@Override
	public List<Off> calendarOffSelect(String storeNo) {
		
		return mapper.calendarOffSelect(storeNo);
	}
	
	// 지정 휴무일 중복 검색 
	@Override
	public int calendarOffCheck(Off inputOff) {
		
		return mapper.calendarOffCheck(inputOff);
	}
	
	// 지정 휴무일 등록
	@Override
	public int calendarOffInsert(Off inputOff) {
		
		return mapper.calendarOffInsert(inputOff);
	}
	
	// 지정 휴무일 변경 
	@Override
	public int calendaroffUpdate(Off inputOff) {
		
		return mapper.calendaroffUpdate(inputOff);
	}

	// 지정 휴무일 삭제 
	@Override
	public int calendaroffDelete(int offDayNo) {
		
		return mapper.calendaroffDelete(offDayNo);
	}
	
	// ------ 예약 관리 ------
	
	// 전체 예약 조회
	@Override
	public List<Reservation> reservAll(int memberNo) {
		return mapper.reservAll(memberNo);
	}
	
	// 예약 조회
	@Override
	public List<Reservation> selectReserv(String storeNo, String reservStatusFl) {

		Map<String, String> map = new HashMap<>();
		map.put("storeNo", storeNo);
		map.put("reservStatusFl", reservStatusFl);
		
		return mapper.selectReserv(map);
	}
	
	// 예약 승인
	@Override
	public int updateReservStatus(int reservNo) {
		
		return mapper.updateReservStatus(reservNo);
	}
	
	// 예약 거절
	@Override
	public int rejectReservStatus(int reservNo) {
		
		return mapper.rejectReservStatus(reservNo);
	}

	// 노쇼 등록
	@Override
	public int noshowReserv(Map<String, Object> map) {
		
		int memberFlag = mapper.selectFlag(map); // 회원 경고 횟수 조회
		
		int result = mapper.noshowReservStatus(map); // 노쇼 상태로 변경
		
		if(result > 0) {
			
			// 경고 횟수 증가
			memberFlag++;
			map.put("memberFlag", memberFlag);
			result = mapper.updateFlag(map); 
		}
		return result;
	}

	// 노쇼 취소
	@Override
	public int noshowReserv(int reservNo) {

		return mapper.noshowReserv(reservNo);
	}


	// 확정된 예약 조회
	@Override
	public List<Reservation> reservConfirm(String storeNo) {
		return mapper.reservConfirm(storeNo);
	}
	
	// 예약 1건 자세히 조회
	@Override
	public Reservation reservDetail(int reservNo) {
		
		return mapper.reservDetail(reservNo);
	}

	// ------ 사장님 정보 ------

	// 사장님 정보 변경 화면으로 전환
	@Override
	public Member selectCeoInfo(int memberNo) {
		return mapper.selectCeoInfo(memberNo);
	}


	// 사장님 정보 변경
	@Override
	public int ceoInfoUpdate(Member inputMember) {
		return mapper.ceoInfoUpdate(inputMember);
	}
	
	// 사장님 비밀번호 변경
	@Override
	public int ceoPwUpdate(int memberNo, Map<String, Object> map) {
		
		String originPw = mapper.selectPw(memberNo);
		
		if( !bcrypt.matches((String)map.get("memberPw"), originPw)) return 0;
		
		String encPw = bcrypt.encode((String)map.get("memberNewPw"));
		
		map.put("encPw", encPw);
		map.put("memberNo", memberNo);
		
		return mapper.ceoPwUpdate(map);
	}

	// ------ 리뷰 ------

	// 사장님 리뷰 조회
	@Override
	public List<Review> reviewAll(int memberNo) {
		return mapper.reviewAll(memberNo);
	}
	
	// 사장님 답변 조회
	@Override
	public List<Review> reviewReply(int memberNo) {
		return mapper.reviewReply(memberNo);
	}
	
	
	// 사장님 미답변 조회
	@Override
	public List<Review> reviewAllNoReply(int memberNo) {
		return mapper.reviewAllNoReply(memberNo);
	}

	// 사장님 댓글 삽입
	@Override
	public int insertReply(ReviewReply inputReply) {
		return mapper.insertReply(inputReply);
	}




	//사장님 댓글 수정
	@Override
	public int updateReply(Map<String, Object> map) {
		return mapper.updateReply(map);
	}
	
	// 사장님 댓글 삭제
	@Override
	public int deleteReply(int replyNo) {
		return mapper.deleteReply(replyNo);
	}


	






}
