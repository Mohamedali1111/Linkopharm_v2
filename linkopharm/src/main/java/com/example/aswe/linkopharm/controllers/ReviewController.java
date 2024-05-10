package com.example.aswe.linkopharm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/reports")
    public ModelAndView showReports() {
        ModelAndView modelAndView = new ModelAndView("reports");
        Iterable<Review> reviews = reviewRepository.findAll();
        modelAndView.addObject("reviews", reviews);
        return modelAndView;
    }

    @PostMapping("/delete-review")
    public ModelAndView deleteReview(@RequestParam Long id) {
        reviewRepository.deleteById(id);
        return new ModelAndView("redirect:/reports");
    }

}
