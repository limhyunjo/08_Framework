package com.kh.test.user.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.test.user.model.dto.User;
import com.kh.test.user.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImple implements UserService {

	
	private final UserMapper mapper;

	@Override
	public User userSearch(String userId) {
		
		return mapper.userSearch(userId);
	}

	


}
