package com.arvato.spring.security;


import com.arvato.spring.models.Account;
import com.arvato.spring.repositories.LoginRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends BasicAuthenticationFilter {

    private LoginRepository loginRepo;
    private JWTTokenUtil jwtTokenUtil;

    public JwtRequestFilter(AuthenticationManager authenticationManager, LoginRepository loginRepo, JWTTokenUtil jwtTokenUtil) {
        super(authenticationManager);
        this.loginRepo = loginRepo;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        final String requestTokenHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;
        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Account account = this.loginRepo.findByUsername(username).block();
            // if token is valid configure Spring Security to manually set
            // authentication
            if (account != null && jwtTokenUtil.validateToken(jwtToken, account.getUsername())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(account, null);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }

}