package com.example.springbootdemo.controllers;

import com.example.springbootdemo.models.AuthModel;
import com.example.springbootdemo.repos.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController
{
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/reg")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request)
    {
        if (authRepository.existsByUsername(request.username()))
        {
            return ResponseEntity.badRequest().build();
        }

        AuthModel authUser = new AuthModel();
        authUser.setUsername(request.username());
        authUser.setPassword(passwordEncoder.encode(request.password()));
        authRepository.save(authUser);

        return ResponseEntity.ok().build();
    }

    public record UserRegistrationRequest(String username, String password) {}
}