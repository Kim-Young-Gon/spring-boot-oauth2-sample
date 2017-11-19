package com.example.config;

import com.example.service.UserDetailService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.AuthorizationServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.authserver.OAuth2AuthorizationServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class JwtOAuth2AuthorizationServerConfiguration extends OAuth2AuthorizationServerConfiguration {
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailService userDetailService;

    public JwtOAuth2AuthorizationServerConfiguration(final BaseClientDetails details,
                                                     final AuthenticationManager authenticationManager,
                                                     final ObjectProvider<TokenStore> tokenStoreProvider,
                                                     final AuthorizationServerProperties properties) {
        super(details, authenticationManager, tokenStoreProvider, properties);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        super.configure(endpoints);
        // OAuth2 기본 Url을 별도로 구현한 경우 해당 매핑으로 변경 시 pathMapping 을 사용한다.
        endpoints//.pathMapping("/oauth/token_key", "/oauth/tonek_key")
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter)
                .userDetailsService(userDetailService);
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }
}
