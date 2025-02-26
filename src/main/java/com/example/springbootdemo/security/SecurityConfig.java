package com.example.springbootdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http
            , AuthenticationManager authenticationManager
            , ObjectMapper objectMapper
            , JwtUtil jwtUtil
            , UserDetailsService userDetailsService) throws Exception
    {
        JsonAuthCustomFilter jsonFilter = new JsonAuthCustomFilter(objectMapper, jwtUtil);
        jsonFilter.setAuthenticationManager(authenticationManager);

        JwtAuthFilter jwtFilter = new JwtAuthFilter(jwtUtil, userDetailsService);

        http
                .addFilterBefore(jsonFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception
    {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        var userDetailsService = new InMemoryUserDetailsManager();

        var user = User.withUsername("user")
                .password("{noop}password")
                .roles("ADMIN")
                .build();

        userDetailsService.createUser(user);

        return userDetailsService;
    }
    //    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager, ObjectMapper objectMapper) throws Exception
//    {
//        JsonAuthCustomFilter jsonFilter = new JsonAuthCustomFilter(objectMapper);
//        jsonFilter.setAuthenticationManager(authenticationManager);
//
//        http
//                .addFilterBefore(jsonFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/login").permitAll() // Разрешить доступ к /login без аутентификации
//                        .anyRequest().authenticated()
//                )
//                .csrf(AbstractHttpConfigurer::disable); // Отключаем CSRF защиту, если не используется
//
//        return http.build();
//    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
//    {
//        http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().authenticated()
//                )
//                .formLogin(withDefaults());
//        return http.build();
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

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
//    {
//        http
//                .authorizeHttpRequests((authorize) -> authorize
//                        .anyRequest().permitAll() // Разрешить доступ ко всем запросам
//                )
//                .csrf(AbstractHttpConfigurer::disable); // Отключить CSRF защиту
//        return http.build();
//    }
}
