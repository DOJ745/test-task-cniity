//package com.example.springbootdemo.security;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//
//import java.io.IOException;
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig
//{
//    private final JwtUtils jwtUtils;
//
//    public SecurityConfig(JwtUtils jwtUtils)
//    {
//        this.jwtUtils = jwtUtils;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(
//            HttpSecurity http,
//            ObjectMapper objectMapper,
//            AuthenticationManager authenticationManager
//    ) throws Exception
//    {
//
//        JsonAuthFilter jsonAuthFilter = new JsonAuthFilter(objectMapper);
//        jsonAuthFilter.setAuthenticationManager(authenticationManager);
//        jsonAuthFilter.setAuthenticationSuccessHandler(this::loginSuccessHandler);
//        jsonAuthFilter.setAuthenticationFailureHandler(this::loginFailureHandler);
//
//        http
//                .addFilterBefore(jsonAuthFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new JwtAuthFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//
//                .authorizeHttpRequests(authorize -> authorize
//                                //.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Разрешить OPTIONS
//                                .requestMatchers("/login").permitAll()
//                                .requestMatchers("/users/**").authenticated()
//                        .anyRequest().permitAll()
//                )
//                .cors(cors -> cors.configurationSource(request -> {
//                            CorsConfiguration config = new CorsConfiguration();
//                            config.setAllowedOrigins(List.of("http://localhost:8080"));
//                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//                            config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
//                            config.setExposedHeaders(List.of("Set-Cookie"));
//                            config.setAllowCredentials(true);
//                            return config;
//                        }))
//                .csrf(AbstractHttpConfigurer::disable);
//
//        return http.build();
//    }
//
//    private void loginSuccessHandler(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Authentication authentication
//    ) throws IOException
//    {
//        String token = jwtUtils.generateToken(authentication.getName());
//
//        Cookie cookie = new Cookie("JWT", token);
//        cookie.setHttpOnly(true);
//        cookie.setSecure(false);
//        cookie.setPath("/");
//        cookie.setMaxAge(86400); // 24 часа
//        response.addCookie(cookie);
//
//        response.setStatus(HttpStatus.OK.value());
//        response.getWriter().write("Login successful!");
//    }
//
//    private void loginFailureHandler(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException exception
//    ) throws IOException
//    {
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.getWriter().write("Login failed: " + exception.getMessage());
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception
//    {
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailsService);
//        return authenticationManagerBuilder.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder encoder)
//    {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(encoder.encode("password"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
//
//
//}
//
////    @Bean
////    public AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception
////    {
////        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
////        authenticationManagerBuilder.userDetailsService(userDetailsService);
////        return authenticationManagerBuilder.build();
////    }
////
////    @Bean
////    public UserDetailsService userDetailsService()
////    {
////        var userDetailsService = new InMemoryUserDetailsManager();
////
////        var user = User.withUsername("user")
////                .password("{noop}password")
////                .roles("USER")
////                .build();
////
////        userDetailsService.createUser(user);
////
////        return userDetailsService;
////    }
////}