package edu.kh.practice.member.service;

import org.springframework.stereotype.Service;

@Service
// 비즈니스 로직 처리 역할 + Bean 등록
public class MemberServiceImpl implements MemberService{

}



/* BCrypt 암호화 (Spring Security 제공)
 * 
 * - 입력된 문자열(비밀번호)에 salt를 추가한 후 암호화
 *  -> 암호화 할 때 마다 결과가 다름
 * 
 * ex)1234 입력 -> $12!asdfg
 * ex)1234 다시 입력 -> $12!qwexs
 * 
 * - 비밀번호 확인 방법
 *  -> BCryptPasswordEncoder.matches(평문 비밀번호, 암호화된 비밀번호)
 *   -> 평문 비밀번호와
 *        암호화된 비밀번호가 같은 경우 true
 *        아니면 false 반환
 *        
 *  로그인 / 비밀번호 변경 / 탈퇴 등 비밀번호가 입력되는 경우
 *  DB에 저장된 암호화된 비밀번호를 조회해서
 *  matches() 메서드로 비교해야 한다!!!
 * */
