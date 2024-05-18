package com.example.aswe.linkopharm.repositories;

import com.example.aswe.linkopharm.models.orderItem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface orderItemRepository extends JpaRepository<orderItem, Integer> {
    List<orderItem> findByOrderId(int OrderId);
}
