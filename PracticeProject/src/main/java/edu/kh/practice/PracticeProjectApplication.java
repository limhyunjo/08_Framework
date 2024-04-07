package edu.kh.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//자동 보안 설정 읽지 말라는 어노테이션
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class PracticeProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeProjectApplication.class, args);
	}

}
