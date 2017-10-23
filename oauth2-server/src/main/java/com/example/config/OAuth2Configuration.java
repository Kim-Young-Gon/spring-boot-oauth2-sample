package com.example.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.access.method.P;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

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
    public ExJwtAccessTokenConverter jwtAccessTokenConverter() {
        return new ExJwtAccessTokenConverter();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Primary
    @Bean
    @Qualifier("primary")
    public JdbcClientDetailsService jdbcClientDetailsService() {
        return new JdbcClientDetailsService(oauth2DataSource());
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
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(userDataSource());
        entityManagerFactoryBean.setPackagesToScan(new String[] {"com.example.user.model"});
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        return entityManagerFactoryBean;
    }

    @Bean
    @Qualifier("user")
    public PlatformTransactionManager userTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(userEntityManager().getObject());
        return transactionManager;
    }
}
