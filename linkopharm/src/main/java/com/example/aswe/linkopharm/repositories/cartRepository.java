package com.example.aswe.linkopharm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.aswe.linkopharm.models.cart;

public interface CartRepository extends JpaRepository<cart, Integer> {
    List<cart> findByUserId(int userId);
    
}
