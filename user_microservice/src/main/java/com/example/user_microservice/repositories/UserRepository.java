package com.example.user_microservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user_microservice.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmail(String email);
    User findById(int id);
}