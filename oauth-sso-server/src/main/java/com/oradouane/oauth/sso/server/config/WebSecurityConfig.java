package com.oradouane.oauth.sso.server.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER - 2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        /*http
                .headers()
                .frameOptions().disable()
                .and()
                .csrf().disable()
                .requestMatchers()
                .antMatchers("/", "/login", "/oauth/authorize", "/oauth/token")
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll();*/
        // @formatter:on
        http
                .requestMatchers()
                .antMatchers("/login", "/oauth/authorize", "/oauth/token")
                .and()
                .formLogin().permitAll()
                .and()
                .anonymous().disable()
                .authorizeRequests().anyRequest().authenticated()
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // @formatter:off
        auth
                .inMemoryAuthentication()
                .withUser("root")
                .password("{noop}root") // instead of NoOpPasswordEncoder bean
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{noop}pass")
                .roles("USER");
        // @formatter:on
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
