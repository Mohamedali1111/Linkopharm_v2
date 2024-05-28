package com.example.aswe.linkopharm.repositories;

import com.example.aswe.linkopharm.models.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface categoryRepository extends JpaRepository<category, Integer> {

}