package com.example.springbootdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.Map;

public class JsonAuthCustomFilter extends AbstractAuthenticationProcessingFilter
{
    private static final Logger logger = LoggerFactory.getLogger(JsonAuthCustomFilter.class);
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    public JsonAuthCustomFilter(ObjectMapper objectMapper, JwtUtil jwtUtil)
    {
        super(new AntPathRequestMatcher("/login", "POST"));
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException
    {
        Map<String, String> credentials = objectMapper.readValue(request.getInputStream(), Map.class);
        String username = credentials.get("username");
        String password = credentials.get("password");

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);

        return getAuthenticationManager().authenticate(authRequest);
    }

//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            FilterChain chain, Authentication authResult) throws IOException
//    {
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//        String token = jwtUtil.generateToken((UserDetails) authResult.getPrincipal());
//        logger.info("Login successful for user: {}", authResult.getName());
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().write("{\"token\": \"" + token + "\"}");
//    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException
    {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        String token = jwtUtil.generateToken((UserDetails) authResult.getPrincipal());

        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(false);
        jwtCookie.setSecure(false);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(7 * 24 * 60 * 60); // 7 дней
        response.addCookie(jwtCookie);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("{\"message\": \"Login successful\"}");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException
    {
        logger.error("Login failed for user");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"message\": \"Login failed\" " + failed.getMessage() + "}");
    }
}
