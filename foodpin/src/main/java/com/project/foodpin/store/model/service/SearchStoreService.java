package com.project.foodpin.store.model.service;

import java.util.List;
import java.util.Map;

import com.project.foodpin.store.model.dto.Store;

public interface SearchStoreService {


	/** 가게 상세 검색
	 * @param map
	 * @return
	 */
	Store storeSearchDetail(Map<String, Object> map);

}
