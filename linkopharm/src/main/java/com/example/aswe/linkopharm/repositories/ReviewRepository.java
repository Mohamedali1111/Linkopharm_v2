package com.example.aswe.linkopharm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.aswe.linkopharm.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}

