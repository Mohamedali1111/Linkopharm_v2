package com.example.aswe.linkopharm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class checkoutController {
    
    @GetMapping("checkout")
    public String checkout(){
        return "checkout";
    }

    @PostMapping("checkout")
    public String confirmation(){
        return "redirect:/";
    }

}




