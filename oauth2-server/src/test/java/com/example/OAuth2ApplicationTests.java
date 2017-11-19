package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Base64;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OAuth2Application.class)
public class OAuth2ApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void makeBasicAuth() {
		final String auth = "client_app:client_app_secret";
		final String tmp = Base64.getEncoder().encodeToString(auth.getBytes());
		System.out.println(String.format("Basic %s", tmp));
	}
}
