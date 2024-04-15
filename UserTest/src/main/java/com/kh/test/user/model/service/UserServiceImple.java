package com.kh.test.user.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.test.user.model.dto.User;
import com.kh.test.user.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // mapper 의존성 주입
@Transactional
@Service // spring에서 생성하고 관리해주는 bean으로 등록해주겠다
public class UserServiceImple implements UserService {

	
	// mapper 의존성 주입 (DI)
	private final UserMapper mapper; 

	@Override
	public User userSearch(String userId) {
		
		return mapper.userSearch(userId);
	}

	@Override
	public List<User> selectName(String inputName) {
		
		return null;
	}

	


}
