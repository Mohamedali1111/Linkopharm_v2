package com.example.aswe.linkopharm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class productsController {
    
    @GetMapping("products")
    public String products(){
        return "products";
    }

}