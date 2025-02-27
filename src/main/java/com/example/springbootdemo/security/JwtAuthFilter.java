package com.example.springbootdemo.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtAuthFilter extends OncePerRequestFilter
{

    private final JwtUtils jwtUtils;

    public JwtAuthFilter(JwtUtils jwtUtils)
    {
        this.jwtUtils = jwtUtils;
    }


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException
    {
        Cookie[] cookies = request.getCookies();

        if (cookies == null)
        {
            filterChain.doFilter(request, response);
            return;
        }

        String token = Arrays.stream(cookies)
                .filter(c -> "JWT".equals(c.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (token != null)
        {
            try
            {
                Claims claims = jwtUtils.parseToken(token);

                Authentication auth = new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        List.of(new SimpleGrantedAuthority("USER"))
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            catch (Exception e)
            {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid JWT");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
