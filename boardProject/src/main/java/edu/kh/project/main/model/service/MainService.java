package edu.kh.project.main.model.service;

public interface MainService {

	// 비밀번호 초기화
	int resetPw(int inputNo);

	// 회원 탈퇴 복구
	int resetSecession(int inputNo);

}
