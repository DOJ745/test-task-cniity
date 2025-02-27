package com.example.springbootdemo.repos;

import com.example.springbootdemo.models.AuthModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<AuthModel, Long>
{
    boolean existsByUsername(String username);
}
