package com.ebki.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ebki.config.JwtConfig;
import com.ebki.model.Authenticate;
import com.ebki.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private JwtConfig jwtConfig;
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Authenticate authenticationInput = new ObjectMapper().readValue(request.getInputStream(), Authenticate.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationInput.getUsername(), authenticationInput.getPassword());
            return authenticationManager.authenticate(authentication);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecurityKey().getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                        .withExpiresAt(Util.numberOfWeek(jwtConfig.getExpirationAfterDays()))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles",
                                user.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
        response.setHeader("access_token", access_token);
    }
}
