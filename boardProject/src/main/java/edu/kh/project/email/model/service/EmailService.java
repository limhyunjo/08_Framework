package edu.kh.project.email.model.service;

public interface EmailService {

	/** 이메일 보내기
	 * @param string
	 * @param email
	 * @return authKey (인증번호)
	 */
	String sendEmail(String string, String email);

}
