package com.arvato.spring.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.
                inMemoryAuthentication()
//                .passwordEncoder(encoder())
                .withUser("giku")
                .password("bramburici")
                .authorities("ROLE_USER")
                .and()
                .withUser("vasilika")
                .password("analfaboss")
                .authorities("ROLE_USER");
    }

//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/user/*").hasRole("ROLE_USER")
//                .antMatchers(HttpMethod.PUT, "/user/*").hasRole("ROLE_USER")
//                .antMatchers("/transactions").hasRole("ROLE_USER")
//                .antMatchers(HttpMethod.POST, "/user").permitAll()
//                .antMatchers(HttpMethod.POST, "/login").permitAll();
//    }

    @Bean
    protected BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
