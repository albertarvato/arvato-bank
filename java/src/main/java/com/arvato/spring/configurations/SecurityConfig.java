package com.arvato.spring.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and().authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/transaction/**").permitAll()
                .antMatchers("/ws/**").permitAll()
                .antMatchers("/transaction").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.unmodifiableList(Arrays.asList("http://localhost:4200")));  //set access from all domains
        configuration.setAllowedMethods(Collections.unmodifiableList(Arrays.asList("GET", "POST", "PUT", "DELETE")));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.unmodifiableList(Arrays.asList(
                "Authorization",
                "Cache-Control",
                "Content-Type",
                "Origin"
        )));


        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
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
