package com.example.aswe.linkopharm.repositories;

import com.example.aswe.linkopharm.models.order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface orderRepository extends JpaRepository<order, Integer> {

    Optional<order> findTopByUserIdOrderByOrderDateDesc(int userId);

    List<order> findByUserId(int userId);

}


