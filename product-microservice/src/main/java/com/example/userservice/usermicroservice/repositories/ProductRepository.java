package com.example.userservice.usermicroservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userservice.usermicroservice.models.products;

@Repository
public interface ProductRepository extends JpaRepository<products, Integer> {

}
