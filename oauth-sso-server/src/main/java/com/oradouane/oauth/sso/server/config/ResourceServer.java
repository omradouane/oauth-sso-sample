package com.oradouane.oauth.sso.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resource.id}")
    private String resourceId;

    private final DefaultTokenServices tokenServices;
    private final TokenStore tokenStore;

    @Autowired
    public ResourceServer(DefaultTokenServices tokenServices, TokenStore tokenStore) {
        this.tokenServices = tokenServices;
        this.tokenStore = tokenStore;
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .requestMatcher(new SsoRequestedMatcher())
                .csrf().disable()
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/sso/hellouser").access("hasAnyRole('USER')") // for test purpose
                .antMatchers("/sso/helloadmin").hasRole("ADMIN") // for test purpose
                .antMatchers("/sso/**").authenticated()
        ;
        // @formatter:on
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // @formatter:off
        resources
                .resourceId(resourceId)
                .tokenServices(tokenServices)
                .tokenStore(tokenStore);
        // @formatter:on
    }

    private static class SsoRequestedMatcher implements RequestMatcher {
        public boolean matches(HttpServletRequest request) {
            // check if the called resource is "/sso/**"
            String path = request.getServletPath();
            if (path.length() >= 5) {
                path = path.substring(0, 5);
                return path.equals("/sso/");
            } else {
                return false;
            }
        }
    }
}
