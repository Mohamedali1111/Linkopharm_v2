package com.example.aswe.linkopharm.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.User;
import com.example.aswe.linkopharm.repositories.UserRepository;

import java.util.Locale.Category;

import org.hibernate.mapping.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
public ModelAndView saveFruit(@ModelAttribute User user) {
String encoddedPassword=BCrypt.hashpw(user.getPassword( ),BCrypt.gensalt (12)) ;
user.setPassword (encoddedPassword) ;
this. userRepository.save (user) ;
ModelAndView mav = new ModelAndView("redirect:/User/Login");
return mav;



}






     @GetMapping("Login")
     public ModelAndView login() {
         ModelAndView mav = new ModelAndView("login.html");
         User newUser = new User();
         mav.addObject("user", newUser);
         return mav;
     }
     
 
      @PostMapping("Login")
      public ModelAndView loginProcess(@RequestParam("username") String username,@RequestParam("password") String password) {
         
       User dbUser=this.userRepository.findByUsername(username);
       Boolean isPasswordMatched=BCrypt.checkpw(password, dbUser.getPassword());
       ModelAndView mav = new ModelAndView("redirect:/");
       ModelAndView mavv = new ModelAndView("redirect:/User/Login");

       if(isPasswordMatched)
     
       return mav;
       
          else
          return mavv;
      }




    
}