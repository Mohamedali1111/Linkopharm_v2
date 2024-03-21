package com.example.aswe.linkopharm.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.linkopharm.models.User;
import com.example.aswe.linkopharm.repositories.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
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
        if (!user.getPassword().equals(user.getConfirmPassword())) {
          
            ModelAndView mav = new ModelAndView("signup.html");
            result.rejectValue("password", "error.user", "Password and Confirm Password must match");
            return mav;
        }

        // Hash and save the password
        String encodedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encodedPassword);
        String encodedConfrmPassword = BCrypt.hashpw(user.getConfirmPassword(), BCrypt.gensalt(12));
        user.setConfirmPassword(encodedConfrmPassword);

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
    public RedirectView loginProcess(@RequestParam("email") String email, @RequestParam("password") String password) {
        User dbUser = this.userRepository.findByEmail(email);

        if (dbUser != null && BCrypt.checkpw(password, dbUser.getPassword())) {
          
            if ("admin".equals(dbUser.getRole())) {
                return new RedirectView("/dashboard");
            } else {
                return new RedirectView("/");
            }
        } else {
            return new RedirectView("/User/Login");
        }
    }



    @GetMapping("profile")
    public ModelAndView profile() {
        ModelAndView mav = new ModelAndView("profile.html");
        User newUser = new User();
        mav.addObject("user", newUser);
        return mav;
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

 // Admin adds user directly redirecting differently
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


 @GetMapping("/edit/{id}")
public ModelAndView showEditUserForm(@PathVariable("id") Integer id) {
    ModelAndView mav = new ModelAndView("editcust");
    User user = userRepository.findById(id)
                  .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    mav.addObject("user", user);
    return mav;
}


@PostMapping("/update")
public ModelAndView updateUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
    if (result.hasErrors()) {
        return new ModelAndView("editcust").addObject("user", user);
    }
    
    User existingUser = userRepository.findById(user.getId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + user.getId()));

    existingUser.setFirstname(user.getFirstname());
    existingUser.setLastname(user.getLastname());
    existingUser.setUsername(user.getUsername());
    existingUser.setEmail(user.getEmail());

    userRepository.save(existingUser);

    return new ModelAndView("redirect:/User");
}


@GetMapping("/delete/{id}")
public ModelAndView deleteUser(@PathVariable("id") int id) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    userRepository.delete(user);
    return new ModelAndView("redirect:/User");
}


  

}
