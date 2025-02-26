package com.example.springbootdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   //AuthenticationManager authenticationManager,
                                                   ObjectMapper objectMapper,
                                                   UserDetailsService userDetailsService) throws Exception
    {

        http
                .authorizeHttpRequests((authorize) -> authorize
                        //.requestMatchers("/login").permitAll()
                        //.requestMatchers("/users/**").authenticated()
                        .anyRequest().permitAll()
                )
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(List.of("http://localhost:8080"));
                    config.setAllowedMethods(List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception
//    {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailsService);
//        return authenticationManagerBuilder.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        var userDetailsService = new InMemoryUserDetailsManager();
//
//        var user = User.withUsername("user")
//                .password("{noop}password")
//                .roles("USER")
//                .build();
//
//        userDetailsService.createUser(user);
//
//        return userDetailsService;
//    }
}