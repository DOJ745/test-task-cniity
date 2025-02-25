package com.example.springbootdemo.services;

import com.example.springbootdemo.file_operations.XmlAsyncReader;
import com.example.springbootdemo.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.springbootdemo.repos.UserRepository;

import java.util.ArrayList;


@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public Mono<ResponseEntity<String>> createUser(User user)
    {
        if (user.getId() <= 0)
        {
            return Mono.just(ResponseEntity.badRequest().body("Invalid user ID"));
        }

        return userRepository.save(user)
                .map(savedUser -> ResponseEntity.ok("New user has been added successfully\nNew data: " + savedUser));
    }

    public Flux<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public Mono<ResponseEntity<String>> getUserById(long id)
    {
        if (id <= 0)
        {
            return Mono.just(ResponseEntity.badRequest().body("{\"error\": \"Invalid user ID\"}"));
        }

        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User with id " + id + " not found")))
                .map(foundUser -> ResponseEntity.ok().body("{\"message\": \"User found\", \"user\": " + foundUser + "}"))
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body("{\"error\": \"" + ex.getMessage() + "\"}")));
    }

    public Mono<ResponseEntity<String>> updateUser(long id, User user)
    {
        if (id <= 0)
        {
            return Mono.just(ResponseEntity.badRequest().body("Invalid user ID"));
        }

        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User with id " + id + " not found")))
                .flatMap(existingUser ->
                {
                    existingUser.setPersonalInfo(user.getPersonalInfo());
                    existingUser.setEducation(user.getEducation());
                    existingUser.setEmployment(user.getEmployment());
                    existingUser.setContactInfo(user.getContactInfo());
                    existingUser.setSkills(user.getSkills());

                    return userRepository.save(existingUser);
                })
                .map(savedUser -> ResponseEntity.ok("User with id " + id + " updated successfully\nNew data: " + savedUser))
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body("Error: " + ex.getMessage())));
    }


    public Mono<ResponseEntity<String>> deleteUser(long id)
    {
        if (id <= 0)
        {
            return Mono.just(ResponseEntity.badRequest().body("Invalid user ID"));
        }

        return userRepository.deleteById(id)
                .then(Mono.defer(() -> Mono.just(ResponseEntity.ok("User with id " + id + " deleted successfully"))))
                .map(savedUser -> ResponseEntity.ok("User with id " + id + " deleted successfully (map method)"))
                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body("Error: " + ex.getMessage())));
    }

    public ArrayList<User> getUsersFromXml()
    {
        XmlAsyncReader xmlAsyncReader = new XmlAsyncReader(XmlAsyncReader.DEFAULT_THREAD_POOL_SIZE);

        final ArrayList<User> users = new ArrayList<>();

        XmlAsyncReader.UserHandler userHandler = users::add;

        String filePath = XmlAsyncReader.DEFAULT_FILE_PATH;

        xmlAsyncReader.readXmlFile(filePath, userHandler);

        return users;
    }

    public Mono<ResponseEntity<String>> addUsersFromXml()
    {
        return userRepository.saveAll(getUsersFromXml()).then(Mono.defer(() -> Mono.just(ResponseEntity.ok("New users have been added successfully"))));
    }
}