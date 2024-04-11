package com.kh.test.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Getter, Setter 무조건 필요
@Getter
@Setter

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private int  userNo; // 유저 번호
	private String userId; // 유저 아이디
	private String userName; // 유저 이름
	private int userAge; // 유저 나이
}
