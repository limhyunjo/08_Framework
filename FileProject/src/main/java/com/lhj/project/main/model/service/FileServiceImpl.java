package com.lhj.project.main.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lhj.project.main.model.mapper.FileMapper;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService{

	private final FileMapper mapper; 
	
}
