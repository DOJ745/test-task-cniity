package com.example.springbootdemo.controllers;

import com.example.springbootdemo.models.User;
import com.example.springbootdemo.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public Flux<User> getAllUsers()
    {
        logger.info("Received GET request (all users)");

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<String>> getUserById(@PathVariable long id)
    {
        logger.info("Received GET request");

        return userService.getUserById(id);
    }

    @GetMapping("/xml")
    public ArrayList<User> getUsersFromXml()
    {
        return userService.getUsersFromXml();
    }

    @PostMapping("/xml")
    public Mono<ResponseEntity<String>> addUsersFromXml()
    {
        return userService.addUsersFromXml();
    }

    @PostMapping
    public Mono<ResponseEntity<String>> createUser(@RequestBody User user)
    {
        logger.info("Received POST request to create user: {}", user.toString());

        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> updateUser(@RequestBody User updateUser, @PathVariable long id)
    {
        logger.info("Received PUT request to update user with id: {}\nNew data {}", id, updateUser.toString());

        return userService.updateUser(id, updateUser);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> deleteUser(@PathVariable long id)
    {
        logger.info("Received DELETE request to update user with id: {}", id);

        return userService.deleteUser(id);
    }
}
