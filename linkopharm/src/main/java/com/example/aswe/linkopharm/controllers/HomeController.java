package com.example.aswe.linkopharm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.aswe.linkopharm.repositories.ReviewRepository;

@Controller
public class HomeController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("reviews", reviewRepository.findAll());
        return "index";
    }
}
