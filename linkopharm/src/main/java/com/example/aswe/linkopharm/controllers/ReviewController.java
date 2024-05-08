package com.example.aswe.linkopharm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.aswe.linkopharm.models.Review;
import com.example.aswe.linkopharm.repositories.ReviewRepository;

@Controller
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/review")
    public String showReviewForm() {
        return "reviewForm";
    }

    @PostMapping("/review")
    public String submitReview(Review review) {
        reviewRepository.save(review);
        return "redirect:/";
    }
}
