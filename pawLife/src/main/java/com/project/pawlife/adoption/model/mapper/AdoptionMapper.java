package com.project.pawlife.adoption.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import com.project.pawlife.adoption.model.dto.Adopt;

@Mapper
public interface AdoptionMapper {

	
	/** 게시글 작성
	 * @param inputAdopt
	 * @return
	 */
	int adoptionInsert(Adopt inputAdopt);

	/** 게시글 수 조회
	 * @return
	 */
	int getListCount();

	/** 게시글 리스트 조회
	 * @param rowBounds
	 * @return
	 */
	List<Adopt> selectAdoptList(RowBounds rowBounds);

	/** 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Adopt selectOneAdopt(Map<String, Integer> map);


	/** 북마크 해제(DELETE)
	 * @param map
	 * @return
	 */
	int deleteBookCheck(Map<String, Integer> map);

	/** 북마크 체크(INSERT)
	 * @param map
	 * @return
	 */
	int insertBookCheck(Map<String, Integer> map);

	/** 수정
	 * @param adoptInput
	 * @return
	 */
	int adoptUpdate(Adopt adoptInput);

	/** 삭제
	 * @param map
	 * @return
	 */
	int adoptDelete(Map<String, Integer> map);

	/** 문의 페이지 DB 저장
	 * @param contactInput
	 * @return
	 */
	int insertContact(Adopt contactInput);

	/** 작성자 이메일 찾기
	 * @param adoptNo
	 * @return
	 */
	String writerEmail(int adoptNo);
	/** 삭제 되지 않은 게시글 수 조회
	 * @param paramMap
	 * @return
	 */
	int getSearchCount(Map<String, Object> paramMap);

	/** 검색 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return
	 */
	List<Adopt> selectSearchList(Map<String, Object> paramMap, RowBounds rowBounds);

	/** 이메일에 넣을 동물 이름
	 * @param contactInput
	 * @return
	 */
	String adoptName(int adoptNo);

	/** 문의 받을 이메일
	 * @param memberNo
	 * @return
	 */
	String toEmail(int memberNo);

	/** 조회수 1 증가
	 * @param adoptNo
	 * @return
	 */
	int updateReadCount(int adoptNo);

	/** 조회수 조회
	 * @param adoptNo
	 * @return
	 */
	int selectReadCount(int adoptNo);
	
}
