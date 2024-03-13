package com.example.aswe.linkopharm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.linkopharm.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    
}
