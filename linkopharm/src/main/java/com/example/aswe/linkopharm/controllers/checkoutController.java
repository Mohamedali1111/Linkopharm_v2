package com.example.aswe.linkopharm.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import com.example.aswe.linkopharm.models.order;
import com.example.aswe.linkopharm.repositories.orderRepository;



@RestController 
@RequestMapping("/checkout")
public class checkoutController {
    
    @Autowired
    private orderRepository orderRepository;

    @GetMapping("")
    public ModelAndView checkout() {
        ModelAndView mav = new ModelAndView("checkout"); 
        mav.addObject("Order", new order());
        return mav;
    }

    @PostMapping("/placeOrder")
    public String addOrder(@ModelAttribute order order) {
        order.setStatus("Pending");
        order.setOrderDate(LocalDate.now().toString());
        this.orderRepository.save(order);
        // int orderId = order.getId();
        // return new ModelAndView("redirect:/checkout/confirmationPage/" + orderId);
        return "Added";
    }

    // @GetMapping("/confirmationPage/{orderId}")
    // public String getOrderDetails(@PathVariable("orderId") Integer orderId, Model model) {
    //     order order = this.orderRepository.getById(orderId);
    //     model.addAttribute("order", order);
    //     return "confirmationPage";
    // }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "An error occurred: " + e.getMessage());
        return mav;
    }
}






