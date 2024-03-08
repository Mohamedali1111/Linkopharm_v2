package com.example.aswe.linkopharm.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
    
      @GetMapping("signup")
    public String signup(){
        return "signup";

    }

    
    @GetMapping("login")
    public String login(){
        return "login";

    }
}
