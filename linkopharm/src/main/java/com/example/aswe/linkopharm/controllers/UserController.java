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

           // Check if email ends with specific domains
    if (!user.getEmail().matches(".*@(yahoo|gmail|outlook)\\.(com|net|org)")) {
        result.rejectValue("email", "error.user", "Invalid email domain. Please use Yahoo, Gmail, or Outlook.");
        ModelAndView mav = new ModelAndView("signup.html");
        return mav;
    }

        if (user.getPassword().length() < 4) {
            result.rejectValue("password", "error.user", "Password must be at least 4 characters long");
            ModelAndView mav = new ModelAndView("signup.html");
            return mav;
        }
    
        // Check if names contain only letters
        if (!user.getFirstname().matches("[a-zA-Z]+")) {
            result.rejectValue("firstname", "error.user", "First name must contain only letters");
            ModelAndView mav = new ModelAndView("signup.html");
            return mav;
        }
    
        if (!user.getLastname().matches("[a-zA-Z]+")) {
            result.rejectValue("lastname", "error.user", "Last name must contain only letters");
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
    public ModelAndView loginProcess(@RequestParam("email") String email, 
                                     @RequestParam("password") String password,
                                     HttpSession session) {
        // Find user by email
        User dbUser = this.userRepository.findByEmail(email);
        Boolean isPassword = BCrypt.checkpw(password, dbUser.getPassword());
    
        // Check if user exists and password is correct
        if (dbUser != null && isPassword ) {
            // Set session attribute to store user's email
            session.setAttribute("email", dbUser.getEmail());
            
            // Check user role and redirect accordingly
            if ("admin".equals(dbUser.getRole())) {
                return new ModelAndView("redirect:/dashboard");
            } else {
                return new ModelAndView("redirect:/");
            }
        } else {
            return new ModelAndView("redirect:/User/login");
        }
    }
    


    @GetMapping("profile")
    public ModelAndView profile(@RequestParam("id") int id) {
        ModelAndView mav = new ModelAndView("profile.html");
        User user = userRepository.findById(id);
        mav.addObject("user", user);
        return mav;
    }



    @GetMapping("Logout")
    public RedirectView logout(HttpSession session) {
        // Invalidate session
        session.invalidate();
        return new RedirectView("/"); // Redirect to login page after logout
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
