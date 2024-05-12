package com.example.aswe.linkopharm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.aswe.linkopharm.models.cart;

public interface CartRepository extends JpaRepository<cart, Integer> {
    
}
