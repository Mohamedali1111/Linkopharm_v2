package com.example.aswe.linkopharm.controllers;

import com.example.aswe.linkopharm.models.products;
import com.example.aswe.linkopharm.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public ModelAndView getProducts() {
        ModelAndView mav = new ModelAndView("displayproducts");
        mav.addObject("products", productRepository.findAll());
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addProductForm() {
        ModelAndView mav = new ModelAndView("addproducts");
        mav.addObject("product", new products()); 
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView saveProduct(@ModelAttribute products product) {
        productRepository.save(product);
        
        return new ModelAndView("redirect:/products");
    }
}
