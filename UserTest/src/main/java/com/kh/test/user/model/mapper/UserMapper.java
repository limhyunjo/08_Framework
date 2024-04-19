package com.kh.test.user.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.test.user.model.dto.User;

@Mapper // 이게 있어야 xml이랑 연결됨
public interface UserMapper {


	List<User> searchName(String inputName);


}
