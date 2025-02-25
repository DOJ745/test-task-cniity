package com.example.springbootdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().permitAll() // Разрешить доступ ко всем запросам
                )
                .csrf(AbstractHttpConfigurer::disable); // Отключить CSRF защиту
        return http.build();
    }
}
