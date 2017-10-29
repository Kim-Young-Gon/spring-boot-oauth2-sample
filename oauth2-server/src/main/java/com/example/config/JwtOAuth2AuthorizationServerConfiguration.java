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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class JwtOAuth2AuthorizationServerConfiguration extends OAuth2AuthorizationServerConfiguration {
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private AuthorizationServerTokenServices tokenServices;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailService userDetailService;

    public JwtOAuth2AuthorizationServerConfiguration(BaseClientDetails details,
                                                     AuthenticationManager authenticationManager,
                                                     ObjectProvider<TokenStore> tokenStoreProvider,
                                                     AuthorizationServerProperties properties) {
        super(details, authenticationManager, tokenStoreProvider, properties);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        super.configure(endpoints);
        endpoints.authenticationManager(authenticationManager)
                .tokenServices(tokenServices)
                .tokenStore(tokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .userDetailsService(userDetailService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }
}
