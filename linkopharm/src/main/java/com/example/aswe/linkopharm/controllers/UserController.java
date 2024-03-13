package com.example.aswe.linkopharm.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.User;
import com.example.aswe.linkopharm.repositories.UserRepository;

import org.hibernate.mapping.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/User")

public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("profile")
    public ModelAndView getUsers(){
        ModelAndView mav= new ModelAndView("profile.html");
        java.util.List<User> users  =this.userRepository.findAll();
        mav.addObject("users", users);
        return mav;

    }
   








@GetMapping("Registration")
public ModelAndView addUser() {
ModelAndView mav = new ModelAndView( "signup.html");
User newUser = new User();
mav.addObject ( "user" , newUser) ;
return mav;
}

@PostMapping( "Registration")
public String saveFruit(@ModelAttribute User user) {
String encoddedPassword=BCrypt.hashpw(user.getPassword( ),BCrypt.gensalt (12)) ;
user.setPassword (encoddedPassword) ;
this. userRepository.save (user) ;
return "Added";



}

    
}
