package com.example.springbootdemo.controllers;

import com.example.springbootdemo.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
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
    private UserService userService;


    @GetMapping("/ping")
    public String testGet()
    {
        logger.info("Received GET request");
        return "URL is working";
    }

    @PostMapping
//    public Mono<User> createUser(@RequestBody User user)
//    {
//        logger.info("Received POST request to create user: {}", user.toString());
//
//        //return userRepository.save(user);
//    }

    @PutMapping("/{id}")
    //public  Mono<ResponseEntity<String>> updateUser(@RequestBody User updateUser, @PathVariable long id)
    public Mono<ResponseEntity<User>> updateUser(@PathVariable Long id, @RequestBody User updateUser)
    {
        logger.info("Received PUT request to update user with id: {}\nNew data {}", id, updateUser.toString());

        return userService.findByIdAndUpdate(id, updateUser)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User with id " + id + " not found")))
                .map(ResponseEntity::ok)
                .onErrorResume(ex -> (Mono.just(ResponseEntity.badRequest().body(updateUser))));
                //.defaultIfEmpty(ResponseEntity.notFound().build());

//        return userRepository.findById(id)
//                .switchIfEmpty(Mono.error(new IllegalArgumentException("User with id " + id + " not found")))
//                .flatMap(existingUser ->
//                {
//                    existingUser.setPersonalInfo(updateUser.getPersonalInfo());
//                    existingUser.setEducation(updateUser.getEducation());
//                    existingUser.setEmployment(updateUser.getEmployment());
//                    existingUser.setContactInfo(updateUser.getContactInfo());
//                    existingUser.setSkills(updateUser.getSkills());
//
//                    return userRepository.save(existingUser);
//                })
//                .map(savedUser -> ResponseEntity.ok("User with id " + id + " updated successfully"))
//                .onErrorResume(ex -> Mono.just(ResponseEntity.badRequest().body("Error: " + ex.getMessage())));

//        Query query = Query.query(Criteria.where("id").is(id));
//        Update update = new Update()
//                .set("personalInfo", updateUser.getPersonalInfo())
//                .set("education", updateUser.getEducation())
//                .set("employment", updateUser.getEmployment())
//                .set("contactInfo", updateUser.getContactInfo())
//                .set("skills", updateUser.getSkills());
//
//        return mongoTemplate.updateFirst(query, update, User.class)
//                .map(result -> ResponseEntity.ok("User with id " + id + " updated successfully"))
//                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public String deleteUser(@RequestParam String id)
    {
        logger.info("Received DELETE request to update user with id: {}", id);

        return "DELETE request to delete user with id: " + id;
    }
}
