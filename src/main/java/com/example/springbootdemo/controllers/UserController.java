package com.example.springbootdemo.controllers;

import com.example.springbootdemo.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import com.example.springbootdemo.repos.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController
{

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

//    @PostMapping
//    public Mono<User> createUser(@RequestBody User user)
//    {
//        return userRepository.save(user);
//    }
    @GetMapping("/ping")
    public String testGet()
    {
        logger.info("Received GET request");
        return "URL is working";
    }

    @PostMapping
    public Mono<ResponseEntity<String>> createUser(@RequestBody User user)
    {
        logger.info("Received POST request to create user: {}", user);

        return userRepository.save(user)
                .then(Mono.fromCallable(() -> ResponseEntity.ok("POST received")));
    }
}
