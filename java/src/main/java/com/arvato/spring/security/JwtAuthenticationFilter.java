package com.arvato.spring.security;


import com.arvato.spring.configurations.MyUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;

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
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) {

        MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();
        final String token = jwtTokenUtil.generateToken(userDetails);

        res.addHeader("Authorization", "Bearer " + token);
    }

}