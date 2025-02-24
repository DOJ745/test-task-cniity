package com.example.springbootdemo.services;

import com.example.springbootdemo.models.User;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import com.example.springbootdemo.repos.UserRepository;

@Service
public class UserService
{
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public Mono<User> createUser(User user)
    {
        return userRepository.save(user);
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
}