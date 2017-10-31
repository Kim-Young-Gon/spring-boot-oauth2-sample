package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        String memberListAccess = "(#oauth2.hasScope('read') or #oauth2.hasScope('ALL')) and hasAnyRole('ROLE_CLIENTAPP', 'ROLE_USER')";
        String memeberActionAccess = "(#oauth2.hasScope('write') or #oauth2.hasScope('ALL')) and hasRole('ROLE_USER')";
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                // OAuth2SecurityExpressionMethods 방식
                .antMatchers("/members").access(memberListAccess)
                .antMatchers("/members/**").access(memeberActionAccess)
                // Authorities 확인 방식
//                .antMatchers("/members/**").hasAnyAuthority("ROLE_CLIENTAPP", "ROLE_USER")
                // 전체 허용
//				.antMatchers("/members", "/members/**").permitAll()
                .anyRequest().authenticated();
    }
}
