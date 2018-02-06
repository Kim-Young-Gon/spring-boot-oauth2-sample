package com.example;

import com.example.member.model.Member;
import com.example.member.model.MemberEx;
import com.example.member.repository.MemberExRepository;
import com.example.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

@EnableResourceServer
@SpringBootApplication
public class ApiApplication {

	/**
	 * API를 조회시 출력될 테스트 데이터
	 * @param memberRepository
	 * @return
	 */
	@Bean
	public CommandLineRunner commandLineRunner(MemberRepository memberRepository, MemberExRepository memberExRepository) {
		return args -> {
		    Member member = new Member("이철수", "chulsoo", "test111");
			memberRepository.save(member);
			MemberEx memberEx = new MemberEx("1234", "N", "일반");
			memberEx.setMember(member);
			memberExRepository.save(memberEx);

			member = new Member("김정인", "jungin11", "test222");
			memberRepository.save(member);
			memberEx = new MemberEx("1234", "N", "일반");
			memberEx.setMember(member);
			memberExRepository.save(memberEx);
			memberEx = new MemberEx("12345", "F", "Facebook");
			memberEx.setMember(member);
			memberExRepository.save(memberEx);

			member = new Member("류정우", "jwryu991", "test333");
			memberRepository.save(member);
			memberEx = new MemberEx("12345", "F", "Facebook");
			memberEx.setMember(member);
			memberExRepository.save(memberEx);
			memberEx = new MemberEx("123456", "G", "Google");
			memberEx.setMember(member);
			memberExRepository.save(memberEx);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}
}