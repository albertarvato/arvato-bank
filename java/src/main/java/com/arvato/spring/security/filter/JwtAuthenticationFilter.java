package com.arvato.spring.security.filter;


import com.arvato.spring.models.Account;
import com.arvato.spring.security.JWTTokenUtil;
import com.arvato.spring.security.JwtRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(JWTTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse httpServletResponse) throws AuthenticationException {
        JwtRequest account = new ObjectMapper()
                .readValue(request.getInputStream(), JwtRequest.class);

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        account.getUsername(),
                        account.getPassword(),
                        new ArrayList<>())
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {

        Account account = (Account) auth.getPrincipal();
        final String token = jwtTokenUtil.generateToken(account.getUsername());

        res.addHeader("Authorization", "Bearer " + token);
    }

}