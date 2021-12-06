package com.ebki.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ebki.auth.UserAuthDetail;
import com.ebki.config.JwtConfig;
import com.ebki.model.Authenticate;
import com.ebki.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtConfig jwtConfig;
    private final AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter(JwtConfig jwtConfig, AuthenticationManager authenticationManager) {
        this.jwtConfig = jwtConfig;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Authenticate authenticationInput = new ObjectMapper().readValue(request.getInputStream(), Authenticate.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(authenticationInput.getUsername(), authenticationInput.getPassword());
            return authenticationManager.authenticate(authentication);
        }catch (IOException e) {
            System.out.println();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException, ServletException {
        UserAuthDetail user = (UserAuthDetail) authentication.getPrincipal();
//        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecurityKey().getBytes());
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                        .withExpiresAt(Util.numberOfWeek(jwtConfig.getExpirationAfterDays()))
                        .withIssuer(request.getRequestURI())
                        .withClaim("roles",
                                user.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
        Map<String, String> token = new HashMap<>();
        token.put("access_token", access_token);
        response.setHeader("access_token", access_token);
        response.setHeader("username", user.getUsername());
        token.put("username", user.getUsername());
        user.getAuthorities().forEach(object -> {
            token.put(object.toString(), object.toString());
            if (object.toString().contains("ROLE")) {
                response.setHeader("Role", object.toString());
            } else {
                response.setHeader(object.toString().toLowerCase(), object.toString());
            }
        });

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), token);
    }
}
