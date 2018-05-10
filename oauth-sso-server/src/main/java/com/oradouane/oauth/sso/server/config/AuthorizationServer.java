package com.oradouane.oauth.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 10_000;
    private static final int REFRESH_TOKEN_VALIDITY_SECONDS = 30_000;

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationServer(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // @formatter:off
        endpoints
                .authenticationManager(this.authenticationManager)
                .tokenServices(tokenServices())
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter());
        // @formatter:on
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // @formatter:off
        // everyone can access token, buy only authenticated can check it
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
        // @formatter:on
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.inMemory()
                .withClient("SampleAppOne")
                    .secret("{noop}secret")
                    .scopes("read", "write")
                    .authorizedGrantTypes("authorization_code", "client_credentials", "password", "refresh_token")
                    .resourceIds(resourceId)
                    .autoApprove(true)
                    .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                    .refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
        ;
        // @formatter:on
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("key");
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        return defaultTokenServices;
    }
}
