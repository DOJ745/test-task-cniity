package com.example.springbootdemo.repos;

import com.example.springbootdemo.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<User, String>
{
}
