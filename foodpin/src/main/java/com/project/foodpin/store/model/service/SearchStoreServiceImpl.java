package com.project.foodpin.store.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.foodpin.store.model.dto.Store;
import com.project.foodpin.store.model.mapper.DetailStoreMapper;
import com.project.foodpin.store.model.mapper.SearchStoreMapper;

import lombok.RequiredArgsConstructor;


@Service
@Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
@RequiredArgsConstructor
public class SearchStoreServiceImpl implements SearchStoreService{

	private final SearchStoreMapper mapper;



	@Override
	public Store storeSearchDetail(Map<String, Object> map) {
		
		return mapper.storeSearchDetail(map);
	}




	


}
