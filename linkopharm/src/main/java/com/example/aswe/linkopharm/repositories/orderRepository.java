package com.example.aswe.linkopharm.repositories;

import com.example.aswe.linkopharm.models.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderRepository extends JpaRepository<order, Integer> {
}
