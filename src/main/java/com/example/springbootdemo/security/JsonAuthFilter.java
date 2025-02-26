package com.example.springbootdemo.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

public class JsonAuthFilter extends UsernamePasswordAuthenticationFilter
{
    private final ObjectMapper objectMapper;

    public JsonAuthFilter(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
        super.setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException
    {
        try
        {
            Map<String, String> credentials = objectMapper.readValue(
                    request.getInputStream(),
                    new TypeReference<>() {}
            );

            String username = credentials.get(getUsernameParameter());
            String password = credentials.get(getPasswordParameter());

            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(username, password);

            return this.getAuthenticationManager().authenticate(authRequest);
        }
        catch (IOException e)
        {
            throw new AuthenticationServiceException("Error reading credentials", e);
        }
    }
}
