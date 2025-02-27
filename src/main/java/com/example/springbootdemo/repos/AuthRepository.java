package com.example.springbootdemo.repos;

import com.example.springbootdemo.models.AuthModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthModel, Long>
{
    boolean existsByUsername(String username);
    Optional<AuthModel> findByUsername(String username);
}
