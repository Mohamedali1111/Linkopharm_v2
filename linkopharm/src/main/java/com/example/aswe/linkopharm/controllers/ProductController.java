package com.example.aswe.linkopharm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.products;
import com.example.aswe.linkopharm.repository.ProductRepository;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository  productRepository;

    @GetMapping("/add")
    public ModelAndView addProductForm() {
        ModelAndView mav = new ModelAndView("addproducts"); 
        mav.addObject("product", new products());
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView addProductSubmit(@ModelAttribute products product) {
        productRepository.save(product);
        return new ModelAndView("redirect:/products/all");
    }

    @GetMapping("/all")
    public ModelAndView getAllProducts() {
        ModelAndView mav = new ModelAndView("displayproducts"); 
        List<products> productList = productRepository.findAll();
        mav.addObject("products", productList);
        return mav;
    }
}