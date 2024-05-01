package com.example.aswe.linkopharm.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.order;


@RestController 
@RequestMapping("/checkout")
public class checkoutController {
    
    @GetMapping("")
    public ModelAndView checkout() {
        ModelAndView mav = new ModelAndView("checkout"); 
        mav.addObject("Order", new order());
        return mav;
    }

}







