package com.example.aswe.linkopharm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dashboradController {

    @GetMapping("dashboard")
    public String dashboard(){
        return "dashboard";

    }
    @GetMapping("displayproducts")
    public String displayproducts(){
        return "displayproducts";

    }

}
