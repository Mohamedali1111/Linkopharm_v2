package com.example.aswe.linkopharm.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.User;
import com.example.aswe.linkopharm.models.order;
import com.example.aswe.linkopharm.repositories.UserRepository;
import com.example.aswe.linkopharm.repositories.orderRepository;

import jakarta.servlet.http.HttpSession;



@RestController 
@RequestMapping("/checkout")
public class checkoutController {
    
    @Autowired
    private orderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ModelAndView checkout() {
        ModelAndView mav = new ModelAndView("checkout"); 
        mav.addObject("Order", new order());
        return mav;
    }

    @PostMapping("/placeOrder")
    public ModelAndView addOrder(@ModelAttribute order order ,User user,HttpSession session) {
        user = this.userRepository.findByEmail((String) session.getAttribute("email"));
        if (user != null) {
            order.setUserId( user.getId());
            order.setTotalPrice(1800.0);
            order.setStatus("Pending");

            // Set the order date with time
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDateTime = now.format(formatter);
            order.setOrderDate(formattedDateTime);

            this.orderRepository.save(order);
            return new ModelAndView("redirect:/checkout/confirmationPage");
        } else {
            return new ModelAndView("redirect:/User/Login?error=user_not_found");
        }
    }

    @GetMapping("/confirmationPage")
    public ModelAndView getOrderDetails(HttpSession session) {
        User user = this.userRepository.findByEmail((String) session.getAttribute("email"));
        if (user != null) {
            Optional<order> optionalOrder = this.orderRepository.findTopByUserIdOrderByOrderDateDesc(user.getId());
            if (optionalOrder.isPresent()) {
                order lastOrder = optionalOrder.get();
                ModelAndView modelAndView = new ModelAndView("confirmationPage");
                modelAndView.addObject("order", lastOrder);
                return modelAndView;
            } else {
                return new ModelAndView("redirect:/errorPage");
            }
        } else {
            return new ModelAndView("redirect:/errorPage");
        }
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "An error occurred: " + e.getMessage());
        return mav;
    }
}







