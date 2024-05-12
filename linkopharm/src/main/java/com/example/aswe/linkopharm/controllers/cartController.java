package com.example.aswe.linkopharm.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.cart;
import com.example.aswe.linkopharm.repositories.CartRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/cart")
public class cartController {

   
    @Autowired
    private CartRepository cartRepository;

    @GetMapping("")
    public ModelAndView getProducts() {
        ModelAndView mav = new ModelAndView("cart");
        mav.addObject("cartItems", cartRepository.findAll());
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView showAddToCartForm() {
        ModelAndView mav = new ModelAndView("cart.html");
        cart newItem = new cart();
        mav.addObject("cartItem", newItem);
        return mav;
    }

    @PostMapping("/add")
    public String addToCart(@ModelAttribute("cartItem") cart cartItem) {
        cartRepository.save(cartItem);
        return "redirect:/cart";
    }
}
