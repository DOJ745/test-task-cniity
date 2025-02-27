package com.example.springbootdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration
@EnableWebSecurity
public class SecurityConfig
{

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            ObjectMapper objectMapper
    ) throws Exception
    {
        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
                .cors(cors -> cors.configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(List.of("http://localhost:8080"));
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                            config.setExposedHeaders(List.of("Set-Cookie"));
                            config.setAllowCredentials(true);
                            return config;
                        }))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
