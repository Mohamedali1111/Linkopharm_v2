package com.example.aswe.linkopharm.repositories;

import com.example.aswe.linkopharm.models.products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<products, Integer> {

}
