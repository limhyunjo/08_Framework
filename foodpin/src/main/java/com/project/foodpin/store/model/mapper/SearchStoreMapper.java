package com.project.foodpin.store.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.foodpin.store.model.dto.Store;

@Mapper
public interface SearchStoreMapper {





	/** 가게 상세 페이지로 이동 시 
	 * 가게 상세 내용과 storeList를 한번에 조회할 것임
	 * @param map
	 * @return
	 */
	Store storeSearchDetail(Map<String, Object> map);


	
}
