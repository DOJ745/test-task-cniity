package com.example.springbootdemo.controllers;

import com.example.springbootdemo.models.User;
import com.example.springbootdemo.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/test/{id}")
    public Mono<ResponseEntity<User>> getUserTest(HttpServletRequest request, @PathVariable long id)
    {
        logger.info("Тестовый запрос пользователя с ID: {}", id);

        String jwtToken = getJwtFromCookie(request);

        if (jwtToken == null)
        {
            logger.warn("JWT token not found in cookies");
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

        return userService.getUserByIdTestJwt(id, jwtToken)
                .map(response ->
                {
                    if (response.getBody() != null)
                    {
                        return ResponseEntity.ok((User) response.getBody());
                    }
                    else
                    {
                        return ResponseEntity.status(response.getStatusCode()).build();
                    }
                });
    }

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

    @PreAuthorize("isAuthenticated()")
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

    private void checkAuth() throws AccessDeniedException
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated())
        {
            throw new AccessDeniedException("Access denied");
        }
    }

    private String getJwtFromCookie(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
        {
            for (Cookie cookie : cookies)
            {
                if ("JWT".equals(cookie.getName()))
                {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
