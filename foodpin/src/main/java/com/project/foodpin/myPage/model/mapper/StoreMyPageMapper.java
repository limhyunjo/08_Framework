package com.project.foodpin.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.foodpin.member.model.dto.Member;
import com.project.foodpin.myPage.model.dto.Off;
import com.project.foodpin.reservation.model.dto.Reservation;
import com.project.foodpin.review.model.dto.Review;
import com.project.foodpin.review.model.dto.ReviewReply;
import com.project.foodpin.store.model.dto.Category;
import com.project.foodpin.store.model.dto.Menu;
import com.project.foodpin.store.model.dto.Store;
import com.project.foodpin.store.model.dto.StoreCategory;

@Mapper
public interface StoreMyPageMapper {

	
	
	/** 가게 정보 수정 화면 이동
	 * @param memberNo
	 * @return store
	 */
	Store selectstoreInfo(int memberNo);
	
	/** 가게 정보 조회
	 * @param storeNo
	 * @return
	 */
	Store selectstoreInfoJs(String storeNo);

	/** 모든 카테고리 조회
	 * @return
	 */
	List<StoreCategory> selectCategoryAll();

	/** 가게 카테고리 조회
	 * @param storeNo
	 * @return
	 */
	List<StoreCategory> selectCategory(String storeNo);
	
	/** 기존 가게 사진 패스 조회
	 * @param storeNo
	 * @return
	 */
	String selectStoreImg(String storeNo);
	
	/** 가게 정보 수정
	 * @param inputStore
	 * @return result
	 */
	int storeInfoUpdate(Store inputStore);
	
	/** 예약 여부 수정 
	 * @param storeStautus
	 * @return
	 */
	int storeStautusUpdate(Store inputStore);
	
	/** 카테고리 삭제 
	 * @param string
	 * @return 
	 */
	int categoryDelete(String storeNo);

	/** 카테고리 수정
	 * @param categoryMap
	 * @return
	 */
	int categoryUpdate(Map<String, Object> categoryMap);
	
	// ------ 메뉴 ------

	/** 메뉴 조회
	 * @param memberNo
	 * @return menuList
	 */
	List<Menu> menuSelect(String storeNo);
	
	/** 메뉴 번호 조회
	 * @param inputMenuList
	 * @return
	 */
	int selectMenuNo(Menu menu);

	/** 메뉴 삭제 ('N' -> 'Y' 변경)
	 * @param storeNo
	 * @return
	 */
	int deleteAllMenu(String storeNo);

	/** 메뉴 등록
	 * @param inputMenuList
	 * @return
	 */
	int insertMenu(Menu menu);

//
	/** 완전히 동일한 메뉴인지 조회
	 * @param menu
	 * @return
	 */
	int selectSameMenuNo(Menu menu);
	
	/** 메뉴명 제외 변경
	 * @param menu
	 * @return
	 */
	int updateMenu(Menu menu);
	
	// ------ 휴무일 ------
	
	/** 고정 휴무일 개수 조회 (있는지)
	 * @param storeNo
	 * @return count
	 */
	int countOffWeek(String storeNo);
	
	
	/** 기존 고정 휴무일 삭제
	 * @param off
	 * @return offWeekNo
	 */
	int deleteOffWeek(String storeNo);

	/** 고정 휴무일 중복 검색 
	 * @param off
	 * @return result 
	 */
	int calendarOffCheck(Off off);

	/** 고정 휴무일 등록 
	 * @param off
	 * @return result 
	 */
	int insertOffWeek(Off off);
	
	/** 고정 휴무일 조회
	 * @param storeNo
	 * @return offList
	 */
	List<Off> selectWeekOff(String storeNo);

	/** 지정 휴무일 변경
	 * @param storeNo
	 * @return offList
	 */
	List<Off> calendarOffSelect(String storeNo);
	
	/** 지정 휴무일 등록
	 * @param inputOff
	 * @return result
	 */
	int calendarOffInsert(Off inputOff);
	
	/** 지정 휴무일 변경 
	 * @param inputOff
	 * @return
	 */
	int calendaroffUpdate(Off inputOff);

	/** 지정 휴무일 삭제 
	 * @param storeNo
	 * @return
	 */
	int calendaroffDelete(int offDayNo);
	
	// ------ 예약 관리 ------
	
	/** 전체 예약 조회
	 * @param storeNo
	 * @return
	 */
	List<Reservation> reservAll(int memberNo);
	
	/** 예약 조회
	 * @param map
	 * @return reservList
	 */
	List<Reservation> selectReserv(Map<String, String> map);
	
	/** 예약 승인 
	 * @param reservNo
	 * @return result
	 */
	int updateReservStatus(int reservNo);
	
	/** 예약 거절 
	 * @param reservNo
	 * @return result
	 */
	int rejectReservStatus(int reservNo);
	
	/** 노쇼시 예약 상태 변경 (X)
	 * @param map
	 * @return
	 */
	int noshowReservStatus(Map<String, Object> map);
	
	/** 노쇼 취소 
	 * @param reservNo
	 * @return
	 */
	int noshowReserv(int reservNo);
	
	/** 회원 경고 횟수 조회
	 * @param map
	 * @return
	 */
	int selectFlag(Map<String, Object> map);

	
	/** 경고 횟수 증가
	 * @param map
	 * @return
	 */
	int updateFlag(Map<String, Object> map);
	

	/** 3번 이상시 회원 탈퇴
	 * @param map
	 * @return
	 */
	int updateReject(Map<String, Object> map);


	/** 확정된 예약 조회
	 * @param memberNo
	 * @return
	 */
	List<Reservation> reservConfirm(String storeNo);

	
	/** 예약 1건 자세히 조회
	 * @param reservNo
	 * @return
	 */
	Reservation reservDetail(int reservNo);
	
	// ------ 사장님 정보 ------
	
	/** 사장님 정보 변경 화면으로 전환
	 * @param memberNo
	 * @return
	 */
	Member selectCeoInfo(int memberNo);

	/** 사장님 정보 변경
	 * @param inputMember
	 * @return result
	 */
	int ceoInfoUpdate(Member inputMember);
	
	
	/** 기존 암호화 비밀번호 조회
	 * @param memberNo
	 * @return
	 */
	String selectPw(int memberNo);


	/** 비밀번호 변경
	 * @param map
	 * @return
	 */
	int ceoPwUpdate(Map<String, Object> map);

	// ------ 리뷰 ------
	
	/** 사장님 리뷰 조회
	 * @param memberNo
	 * @return
	 */
	List<Review> reviewAll(int memberNo);

	/** 사장님 답변 조회
	 * @param memberNo
	 * @return
	 */
	List<Review> reviewReply(int memberNo);

	/** 사장님 미답변 조회
	 * @param memberNo
	 * @return
	 */
	List<Review> reviewAllNoReply(int memberNo);

	/** 사장님 댓글 삽입
	 * @param inputReply
	 * @return
	 */
	int insertReply(ReviewReply inputReply);
  
	/** 사장님 댓글 수정
	 * @param map
	 * @return
	 */
	int updateReply(Map<String, Object> map);


	/** 사장님 댓글 삭제
	 * @param replyNo
	 * @return
	 */
	int deleteReply(int replyNo);











}
