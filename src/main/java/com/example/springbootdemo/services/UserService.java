package com.example.springbootdemo.services;

import com.example.springbootdemo.file_operations.XmlAsyncReader;
import com.example.springbootdemo.messages.ResponseMsg;
import com.example.springbootdemo.models.User;
import com.example.springbootdemo.structs.MsgTypes;
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
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ResponseMsg.createMsg(MsgTypes.MSG_BAD_REQUEST
                            , "User id must be greater than 0"
                            , null)));
        }

        return userRepository.save(user)
                .map(savedUser ->
                        ResponseEntity.ok(ResponseMsg.createMsg(MsgTypes.MSG_SUCCESS
                                , "User has been created"
                                , savedUser)));
    }

    public Flux<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public Mono<ResponseEntity<String>> getUserById(long id)
    {
        if (id <= 0)
        {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ResponseMsg.createMsg(MsgTypes.MSG_BAD_REQUEST
                            , "User id must be greater than 0"
                            , null)));
        }

        return userRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User with " + id + " not found")))
                .map(foundUser -> ResponseEntity.ok(ResponseMsg.createMsg(MsgTypes.MSG_SUCCESS
                        , "User has been found"
                        , foundUser)))
                .onErrorResume(ex ->
                        Mono.just(ResponseEntity
                                .badRequest()
                                .body(ResponseMsg.createMsg(MsgTypes.MSG_BAD_REQUEST
                                        , ex.getMessage()
                                        , null))));
    }

    public Mono<ResponseEntity<String>> updateUser(long id, User user)
    {
        if (id <= 0)
        {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ResponseMsg.createMsg(MsgTypes.MSG_BAD_REQUEST
                            , "User id must be greater than 0"
                            , null)));
        }

        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User with " + id + " not found")))
                .flatMap(existingUser ->
                {
                    existingUser.setPersonalInfo(user.getPersonalInfo());
                    existingUser.setEducation(user.getEducation());
                    existingUser.setEmployment(user.getEmployment());
                    existingUser.setContactInfo(user.getContactInfo());
                    existingUser.setSkills(user.getSkills());

                    return userRepository.save(existingUser);
                })
                .map(updatedUser ->
                        ResponseEntity.ok()
                        .body(ResponseMsg.createMsg(MsgTypes.MSG_SUCCESS
                                , "User has been updated"
                                , updatedUser)))
                .onErrorResume(ex ->
                        Mono.just(ResponseEntity
                                .badRequest()
                                .body(ResponseMsg.createMsg(MsgTypes.MSG_BAD_REQUEST
                                        , ex.getMessage()
                                        , null))));
    }


    public Mono<ResponseEntity<String>> deleteUser(long id)
    {
        if (id <= 0)
        {
            return Mono.just(ResponseEntity
                    .badRequest()
                    .body(ResponseMsg.createMsg(MsgTypes.MSG_BAD_REQUEST
                            , "User id must be greater than 0"
                            , null)));
        }

        return userRepository.findById(id)
                .flatMap(foundUser ->
                        userRepository
                                .deleteById(id)
                                .then(Mono.just(ResponseEntity.ok(ResponseMsg.createMsg(MsgTypes.MSG_SUCCESS
                                        , "User with id " + id + " has been deleted"
                                        , null))))
                )
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User with " + id + " not found")))
                .onErrorResume(ex ->
                        Mono.just(ResponseEntity
                                .badRequest()
                                .body(ResponseMsg.createMsg(MsgTypes.MSG_INTERNAL_SERVER_ERROR
                                        , "Error deleting user: " + ex.getMessage()
                                        , null))));
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
        return userRepository
                .saveAll(getUsersFromXml())
                .then(
                        Mono.defer(() ->
                                Mono.just(ResponseEntity.ok(ResponseMsg.createMsg(MsgTypes.MSG_SUCCESS
                                        ,"New users have been added successfully"
                                        , null)))));
    }
}