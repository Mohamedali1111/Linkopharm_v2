package com.example.aswe.linkopharm.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;

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

import com.example.aswe.linkopharm.models.User;
import com.example.aswe.linkopharm.models.cart;
import com.example.aswe.linkopharm.repositories.CartRepository;
import com.example.aswe.linkopharm.repositories.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cart")
public class cartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ModelAndView getProducts(HttpSession session) {
        ModelAndView mav = new ModelAndView("cart");
        String email = (String) session.getAttribute("email");
        if (email == null) {
            mav.addObject("cartItems", Collections.emptyList());
        } else {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                mav.addObject("cartItems", cartRepository.findByUserId(user.getId()));
            }
        }
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView showAddToCartForm(HttpSession session) {
        ModelAndView mav = new ModelAndView("cart.html");
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return new ModelAndView("redirect:/User/Login");
        }
        cart newItem = new cart();
        mav.addObject("cartItem", newItem);
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView addToCart(@ModelAttribute("cartItem") cart cartItem, HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return new ModelAndView("redirect:/User/Login");
        }
        User user = userRepository.findByEmail(email);
        if (user != null) {
            cartItem.setUser(user);
            cartRepository.save(cartItem);
        }
        return new ModelAndView("redirect:/cart");
    }
}
