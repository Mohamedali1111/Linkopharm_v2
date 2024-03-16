package com.example.aswe.linkopharm.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.User;
import com.example.aswe.linkopharm.repositories.UserRepository;

import java.util.Locale.Category;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public ModelAndView getUsers() {
        ModelAndView mav = new ModelAndView("profile.html");
        mav.addObject("users", userRepository.findAll());
        return mav;

    }
    // Registration for general user

    @GetMapping("Registration")
    public ModelAndView addUser() {
        ModelAndView mav = new ModelAndView("signup.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @PostMapping("Registration")
    public ModelAndView saveFruit(@ModelAttribute @Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("signup.html");
            return mav;
        }

        // Check if email already exists
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", "error.user", "Email already exists");
            ModelAndView mav = new ModelAndView("signup.html");
            return mav;
        }

        // Hash and save the password
        String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encodedPassword);

        // Save the user
        userRepository.save(user);

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
    public ModelAndView loginProcess(@RequestParam("email") String email, @RequestParam("password") String password) {
        User dbUser = this.userRepository.findByEmail(email);
        ModelAndView mav = new ModelAndView("redirect:/");
        ModelAndView mavv = new ModelAndView("redirect:/User/Login");

        if (dbUser != null && BCrypt.checkpw(password, dbUser.getPassword())) {
            return mav;
        } else {
            return mavv;
        }
    }

    @GetMapping("forgetPassword")
    public ModelAndView forgetPassword() {
        ModelAndView mav = new ModelAndView("forgetPassword.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
    }

    @GetMapping("")
    public ModelAndView showDashboard() {
        ModelAndView mav = new ModelAndView("custdash");
        List<User> users = userRepository.findAll();
        mav.addObject("users", users);
        return mav;
    }

 // Admin adds user directly, redirecting differently
 @GetMapping("/AddUser")
 public ModelAndView addAdminUserForm() {
     ModelAndView mav = new ModelAndView("addcust");
     mav.addObject("user", new User());
     return mav;
 }
 @PostMapping("/AddUser")
 public ModelAndView addAdminUser(@ModelAttribute @Validated User user, BindingResult result) {
     if (result.hasErrors()) {
         return new ModelAndView("addcust");
     }

     if (userRepository.findByEmail(user.getEmail()) != null) {
         result.rejectValue("email", "error.user", "An account with this email already exists.");
         return new ModelAndView("addcust");
     }

     user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
     userRepository.save(user);

     return new ModelAndView("redirect:/User"); // Redirect to dashboard
 }



}
