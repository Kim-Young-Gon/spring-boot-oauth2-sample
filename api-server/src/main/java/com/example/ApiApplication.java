package com.example;

import com.example.member.model.Member;
import com.example.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@EnableResourceServer
@SpringBootApplication
public class ApiApplication {

	/**
	 * API를 조회시 출력될 테스트 데이터
	 * @param memberRepository
	 * @return
	 */
	@Bean
	public CommandLineRunner commandLineRunner(MemberRepository memberRepository) {
		return args -> {
			memberRepository.save(new Member("이철수", "chulsoo", "1234", "test111"));
			memberRepository.save(new Member("김정인", "jungin11", "1234", "test222"));
			memberRepository.save(new Member("류정우", "jwryu991", "1234", "test333"));
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}