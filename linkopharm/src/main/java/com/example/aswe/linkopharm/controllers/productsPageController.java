package com.example.aswe.linkopharm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.aswe.linkopharm.models.products;
import com.example.aswe.linkopharm.repositories.ProductRepository;

@Controller
public class productsPageController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/productsPage")
    public String getProducts(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "productsPage";
    }

    @GetMapping("/productsPage/productDetails/{productId}")
    public String getProductDetails(@PathVariable("productId") Integer productId, Model model) {
        products product = this.productRepository.getById(productId);
        model.addAttribute("product", product);
        return "productDetails";
    }

}



