package com.example.config;

import com.example.endpoint.ClientManageEndpoint;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.user.repository",
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "userTransactionManager"
)
public class OAuth2Configuration {
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "oauth2.datasource")
    @Qualifier("primary")
    public DataSourceProperties oauth2DataSourceProp() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "oauth2.datasource.tomcat")
    @Qualifier("primary")
    public DataSource oauth2DataSource() {
        return oauth2DataSourceProp().initializeDataSourceBuilder().build();
    }

    @Bean
    public ExJwtAccessTokenConverter jwtAccessTokenConverter(ResourceServerProperties properties) {
        final ExJwtAccessTokenConverter tokenConverter = new ExJwtAccessTokenConverter();
        // 키값을 지정하면 서버가 재기동 되어도 jwt token 을 decode 할 수 있음
        tokenConverter.setSigningKey(properties.getJwt().getKeyValue());
        return tokenConverter;
    }

    @Bean
    public TokenStore tokenStore(ExJwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Primary
    @Bean
    @Qualifier("primary")
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(oauth2DataSource());
    }

    /**
     * Custom endpoint 추가
     * UserDetailService에서 권한관리 됨
     * @return
     */
    @Primary
    @Bean
    @Qualifier("primary")
    public ClientManageEndpoint clientManageEndpoint() {
        return new ClientManageEndpoint(jdbcClientDetailsService());
    }

    @Bean
    @ConfigurationProperties(prefix = "user.datasource")
    @Qualifier("user")
    public DataSourceProperties userDataSourceProp() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "user.datasource.tomcat")
    @Qualifier("user")
    public DataSource userDataSource() {
        return userDataSourceProp().initializeDataSourceBuilder().build();
    }

    @Bean
    @Qualifier("user")
    public LocalContainerEntityManagerFactoryBean userEntityManager() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(userDataSource());
        entityManagerFactoryBean.setPackagesToScan(new String[] {"com.example.user.model"});
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        return entityManagerFactoryBean;
    }

    @Bean
    @Qualifier("user")
    public PlatformTransactionManager userTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(userEntityManager().getObject());
        return transactionManager;
    }
}
