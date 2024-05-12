package com.example.aswe.linkopharm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.products;
import com.example.aswe.linkopharm.repositories.ProductRepository;


@Controller
@RequestMapping("/productsPage")
public class productsPageController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public ModelAndView getProducts() {
        ModelAndView mav = new ModelAndView("productsPage.html");
        mav.addObject("products", productRepository.findAll());
        return mav;
    }

    @GetMapping("/productDetails/{productId}")
    public ModelAndView getProductDetails(@PathVariable("productId") String productIdString) {
        Integer productId;
        try {
            productId = Integer.parseInt(productIdString);
        } catch (NumberFormatException e) {
            return new ModelAndView("redirect:/errorPage");
        }
    
        if (!productRepository.existsById(productId)) {
            return new ModelAndView("redirect:/errorPage");
        }
    
        @SuppressWarnings("deprecation")
        products product = productRepository.getById(productId);
        ModelAndView mav = new ModelAndView("productDetails.html");
        mav.addObject("product", product);
        return mav;
    }

    @GetMapping("/productDetails")
    public ModelAndView handleInvalidProductDetails() {
        return new ModelAndView("redirect:/errorPage");
    }

    @GetMapping("/productDetails/")
    public ModelAndView handleInvalidProductDetailsSlash() {
        return new ModelAndView("redirect:/errorPage");
    }
    
}






