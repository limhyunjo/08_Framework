package edu.kh.project.common.scheduling.service;


import java.util.List;

import org.springframework.stereotype.Service;

import edu.kh.project.common.scheduling.mapper.SchedulingMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchedulingServiceImpl implements SchedulingService{

	 private final SchedulingMapper mapper; // 의존성 주입

	@Override
	public List<String> selectImageList() {
		
		return mapper.selectImageList();
	}
	 

}
