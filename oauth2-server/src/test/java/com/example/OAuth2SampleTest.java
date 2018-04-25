package com.example;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Map;

public class OAuth2SampleTest {
    @Test
    public void makeClientSecret() {
        final String password = "app";
        System.out.println(String.format("password : [%s]", password));
        final String encodedPassword = new BCryptPasswordEncoder().encode(password);
        System.out.println(String.format("encoded password : [%s]", encodedPassword));
        final String base64Encoded = Base64.getUrlEncoder().encodeToString(new String(encodedPassword).getBytes());
        System.out.println(String.format("base64 password : [%s]", base64Encoded));
    }

    @Test
    public void makeBasicAuth() {
        final String value = "app:JDJhJDEwJFdwVGxsQ2lEMnlzdEo3LzEzYlV4SHV4TUcvVmV1Y1BQdzZlTkE3T2wueTFJcjkyR2ppVGdD";
        final String base64Encoded = Base64.getUrlEncoder().encodeToString(new String(value).getBytes());
        System.out.println(String.format("base64 value : [%s]", base64Encoded));
    }

    @Test
    public void checkJWTToken() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJtZW1iZXJTZXEiOiJDMDAwMDAwMDA0IiwidXNlcl9uYW1lIjoiaHl1bnN1azc2QGdtYWlsLmNvbSIsInNjb3BlIjpbIkFMTCJdLCJtZW1iZXJOYW1lIjoi7JWI7Jyk7KCVMSIsIm1lbWJlclR5cGUiOiJOIiwiZXhwIjoxNTQwODcxOTQxLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiMTY2NTlmMDktYmYwMC00MmFiLTlkYTMtNmY5ZjRhYzA4Y2UxIiwiY2xpZW50X2lkIjoibmV3c19hcHAifQ.nIMqaINvBrgqEFOLuFL55M4Ha0UzUdJnCWd4WjwMao8";
        String result = JwtHelper.decode(token).getClaims();
        JsonParser objectMapper = JsonParserFactory.create();
        Map<String, Object> object = objectMapper.parseMap(result);
        System.out.println(String.format("TOKEN INFO-all : %s", object));
        String expStr = String.valueOf(object.get("exp"));
        Long expLong = Long.parseLong(expStr);
        System.out.println(String.format("TOKEN INFO-exp : %d", Duration.between(Instant.now(), Instant.ofEpochSecond(expLong)).getSeconds()));
    }

    @Test
    public void createSigningKey() throws Exception {
        String key = "JDJhJDEwJGhEdVE2VC9CVm1INjN3N3Fnd29nU3VyWjhGRmFkbVJQZHEuSzJrT09TUG5hOGJXeERrdUYy";
        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal();
        String result = new String(Base64.getEncoder().encodeToString(rawHmac));
        System.out.println(String.format("SigningKey : [%s]", result));
        System.out.println(String.format("SigningKey : [%s]", new String(rawHmac)));
    }

    @Test
    public void plusYear() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println(String.format("currentDateTime : %s", currentDateTime));
        System.out.println(String.format("currentDateTime + 1year : %s", currentDateTime.plusYears(1).toInstant(ZoneOffset.UTC)));
    }
}
