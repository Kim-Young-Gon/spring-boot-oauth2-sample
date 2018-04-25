package com.example;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Pattern;

public class SampleTest {

    @Test
    public void checkPassword() {
        String inputPw = "12345";
        String dbPw = "$2a$10$ZY36uNxw8sEJbNX6PIf2SObpdgQ8Mj/KInkl/nmd5bKQirE.PkQ2a";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isMatched = passwordEncoder.matches(inputPw, dbPw);
        System.out.println(String.format("inputPw : [%s]", inputPw));
        System.out.println(String.format("dbPw : [%s]", dbPw));
        System.out.println(String.format("isMatched : [%s]", isMatched));
    }

    @Test
    public void regexTest() {
        String regExp = "/api/posting/(\\d{1,}).*";
        regExp = "/api/bookmarkArticle/(\\d|\\w){10}.*";
        String url1 = "/api/posting/123?aaaa=bbbb&cccc=111";
        String url2 = "/api/posting/list?aaaaa";
        String url3 = "/api/bookmarkArticle/C000000033?asaaa=bbbb&cccc=dddd";
        System.out.println(String.format("/api/posting/{postingNumber} : [%s], %b", url1, Pattern.matches(regExp, url1)));
        System.out.println(String.format("/api/posting/list : [%s], %b", url2, Pattern.matches(regExp, url2)));
        System.out.println(String.format("/api/bookmarkArticle/{userSeq} : [%s], %b", url3, Pattern.matches(regExp, url3)));
    }
}
