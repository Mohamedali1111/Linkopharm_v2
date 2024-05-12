package com.example.aswe.linkopharm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller 
@RequestMapping("/errorPage")
public class errorPageController {

    @GetMapping("")
    public ModelAndView error(){
        ModelAndView mav = new ModelAndView("errorPage.html");
        return mav;
    }
}


