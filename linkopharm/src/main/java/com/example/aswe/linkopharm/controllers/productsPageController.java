package com.example.aswe.linkopharm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.aswe.linkopharm.models.category;
import com.example.aswe.linkopharm.models.products;
import com.example.aswe.linkopharm.repositories.ProductRepository;
import com.example.aswe.linkopharm.repositories.categoryRepository;


@Controller
@RequestMapping("/productsPage")
public class productsPageController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private categoryRepository categoryRepository;

    @GetMapping("")
    public ModelAndView getProducts(@RequestParam(defaultValue = "0") int page) {
        int pageSize = 8;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<products> productPage = productRepository.findAll(pageable);

        List<category> categories = categoryRepository.findAll();
        ModelAndView mav = new ModelAndView("productsPage");
        mav.addObject("categories", categories);
        mav.addObject("products", productPage.getContent());
        mav.addObject("totalPages", productPage.getTotalPages());
        mav.addObject("currentPage", page);
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






