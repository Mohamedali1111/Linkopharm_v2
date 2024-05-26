package com.example.aswe.linkopharm.repositories;

import com.example.aswe.linkopharm.models.order;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface orderRepository extends JpaRepository<order, Integer> {

    Optional<order> findTopByUserIdOrderByOrderDateDesc(int userId);

    List<order> findByUserId(int userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE orders SET status = 'Cancelled' WHERE id = :orderId AND status = 'PENDING'", nativeQuery = true)
    int cancelOrder(@Param("orderId") Integer orderId);

}


