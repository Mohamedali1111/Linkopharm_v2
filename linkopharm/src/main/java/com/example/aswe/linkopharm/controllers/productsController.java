package com.example.aswe.linkopharm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.repositories.ProductRepository;

@Controller
@RequestMapping("/products/view")
public class productsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public ModelAndView getProducts() {
        ModelAndView mav = new ModelAndView("products");
        mav.addObject("products", productRepository.findAll());
        return mav;
    }

    @GetMapping("productdDetails")
    public String productdDetails(){
        return "productdDetails";
    }

}