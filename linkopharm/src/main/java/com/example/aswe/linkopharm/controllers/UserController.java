package com.example.aswe.linkopharm.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.aswe.linkopharm.models.User;
import com.example.aswe.linkopharm.models.order;
import com.example.aswe.linkopharm.services.UserService;
import com.example.aswe.linkopharm.repositories.UserRepository;
import com.example.aswe.linkopharm.repositories.orderRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
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
    private UserService userService;

    @Autowired
    private orderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;


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
                                     HttpSession session, RedirectAttributes redirectAttributes) {
        User dbUser = userService.findByEmail(email);
    
        if (dbUser != null && BCrypt.checkpw(password, dbUser.getPassword())) {
            session.setAttribute("email", dbUser.getEmail());
            if ("admin".equals(dbUser.getRole())) {
                return new ModelAndView("redirect:/dashboard");
            } else {
                return new ModelAndView("redirect:/");
            }
        } else {
            redirectAttributes.addAttribute("error", "true");  
            return new ModelAndView("redirect:/User/Login?error=true"); 
        }
    }

    @GetMapping("profile")
    public ModelAndView profile(HttpSession session) {
        ModelAndView mav = new ModelAndView("profile.html");

        
        String email = (String) session.getAttribute("email");

        
        User user = userRepository.findByEmail(email);

        if (user != null) {
            List<order> orders = orderRepository.findByUserId(user.getId());
            if (orders.size() != 0) {
                mav.addObject("orders", orders);
                mav.addObject("user", user);
            } else {
                mav.addObject("orders", null);
                mav.addObject("user", user);
            }
        } else {
            mav.setViewName("redirect:/User/Login?error=user_not_found");
        }

        return mav;
    }

    @PostMapping("/cancelOrder/{orderId}")
    public ModelAndView cancelOrder(@PathVariable Integer orderId, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        
        int updatedRows = orderRepository.cancelOrder(orderId);
        if (updatedRows > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Order cancelled successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to cancel the order.");
        }

        modelAndView.setViewName("redirect:/User/profile");
        return modelAndView;
    }

    @PostMapping("editProfile")
    public ModelAndView editProfile(@ModelAttribute @Valid User updatedUser, BindingResult result,
            HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/User/profile");
        String sessionEmail = (String) session.getAttribute("email");
    
        if (sessionEmail == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to edit your profile.");
            mav.setViewName("redirect:/User/login");
            return mav;
        }
    
        User user = userService.findByEmail(sessionEmail);
        if (user == null) {
            redirectAttributes.addFlashAttribute("notFound", "User not found.");
            return mav;
        }
    
        if (!user.getEmail().equals(updatedUser.getEmail())) {
            redirectAttributes.addFlashAttribute("emailError", "Email changes are not allowed.");
            return mav;
        }
    
   
        if (updatedUser.getFirstname().length() < 2) {
            redirectAttributes.addFlashAttribute("firstnameError", "First name must be at least 2 characters long.");
            return mav;
        }
        if (updatedUser.getLastname().length() < 2) {
            redirectAttributes.addFlashAttribute("lastnameError", "Last name must be at least 2 characters long.");
            return mav;
        }
        if (updatedUser.getUsername().length() < 2) {
            redirectAttributes.addFlashAttribute("usernameError", "Username must be at least 2 characters long.");
            return mav;
        }
    
  
        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setUsername(updatedUser.getUsername());
        userService.save(user);
        redirectAttributes.addFlashAttribute("profileUpdated", "Profile updated successfully.");
    
        return mav;
    }
    @PostMapping("/updatePassword")
    public ModelAndView updatePassword(@RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            HttpSession session, RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("redirect:/User/profile");

        String email = (String) session.getAttribute("email");

        
        User user = userRepository.findByEmail(email);

        if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("invalidPassword", "Current password is incorrect.");
            return mav;
        }

        if (newPassword.length() < 6) {
            redirectAttributes.addFlashAttribute("passwordError", "New password must be at least 6 characters.");
            return mav;
        }

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("passwordMismatch", "New password and confirmation do not match.");
            return mav;
        }

        String encodedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        user.setPassword(encodedNewPassword);
        userService.save(user);
        redirectAttributes.addFlashAttribute("passwordUpdated", "Password updated successfully.");

        return mav;
    }

    @GetMapping("Logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate();
        return new RedirectView("/"); 
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
        List<User> users = userService.findAll();
        mav.addObject("users", users);
        return mav;
    }

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

        if (userService.findByEmail(user.getEmail()) != null) {
            result.rejectValue("email", "error.user", "An account with this email already exists.");
            return new ModelAndView("addcust");
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        userService.save(user);

        return new ModelAndView("redirect:/User"); 
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditUserForm(@PathVariable("id") Integer id) {
        ModelAndView mav = new ModelAndView("editcust");
        User user = userService.findById(id);
                // .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/update")
    public ModelAndView updateUser(@Valid @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("editcust").addObject("user", user);
        }

        User existingUser = userService.findById(user.getId());
        if (existingUser == null) {
            throw new IllegalArgumentException("Invalid user Id:" + user.getId());
        }

        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        userService.save(existingUser);

        return new ModelAndView("redirect:/User");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") int id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        userService.delete(id);
        return new ModelAndView("redirect:/User");
    }
}