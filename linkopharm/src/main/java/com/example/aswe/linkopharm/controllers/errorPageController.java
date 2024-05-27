package com.example.aswe.linkopharm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.error;

@Controller 
public class errorPageController {

    // @GetMapping("/***")
    // public ModelAndView generalError(){
    //     ModelAndView mav = new ModelAndView("errorPage.html");
    //     error error = new error();
    //     error.setErrortitle("404 - Page Not Found");
    //     error.setErrorMessage("Oops! The page you're looking for could not be found.");
    //     mav.addObject("error", error);
    //     return mav;
    // }

    @GetMapping("/errorPage")
    public ModelAndView error(){
        ModelAndView mav = new ModelAndView("errorPage.html");
        error error = new error();
        error.setErrortitle("404 - Page Not Found");
        error.setErrorMessage("Oops! The page you're looking for could not be found.");
        mav.addObject("error", error);
        return mav;
    }

    @GetMapping("/emptyCart")
    public ModelAndView emptyCartError(){
        ModelAndView mav = new ModelAndView("errorPage.html");
        error error = new error();
        error.setErrortitle("Empty Cart");
        error.setErrorMessage("Oops! Your cart is empty. Please add items before placing an order.");
        mav.addObject("error", error);
        return mav;
    }
}








