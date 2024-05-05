package com.example.aswe.linkopharm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.order;
import com.example.aswe.linkopharm.repositories.orderRepository;


@RestController 
@RequestMapping("/checkout")
public class checkoutController {
    //   @Autowired
    // private orderRepository orderRepository;

    // @PostMapping("placeOrder")
    //     public String addOrder(@ModelAttribute order order) {
    //     this.orderRepository.save(order);
    //     return "Added";
    // }

    // @ExceptionHandler(Exception.class)
    // public ModelAndView handleException(Exception e) {
    //     ModelAndView mav = new ModelAndView("error");
    //     mav.addObject("message", "An error occurred: " + e.getMessage());
    //     return mav;
    // }


    @GetMapping("")
    public ModelAndView checkout() {
        ModelAndView mav = new ModelAndView("checkout"); 
        mav.addObject("Order", new order());
        return mav;
    }

}







