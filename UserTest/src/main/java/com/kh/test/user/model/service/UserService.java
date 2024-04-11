package com.kh.test.user.model.service;

import java.util.List;

import com.kh.test.user.model.dto.User;

public interface UserService {

	User userSearch(String userId);

	List<User> selectName(String inputName);

}
