package com.example.aswe.linkopharm.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.models.User;
import com.example.aswe.linkopharm.models.order;
import com.example.aswe.linkopharm.models.orderItem;
import com.example.aswe.linkopharm.repositories.UserRepository;
import com.example.aswe.linkopharm.repositories.orderItemRepository;
import com.example.aswe.linkopharm.repositories.orderRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class receiptPageController {

    @Autowired
    private orderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private orderItemRepository orderItemRepository;

    @GetMapping("/receiptPage/{orderId}")
    public ModelAndView receiptPage(@PathVariable Integer orderId, HttpSession session){
        User user = this.userRepository.findByEmail((String) session.getAttribute("email"));
        if (user != null) {
            Optional<order> optionalOrder = this.orderRepository.findById(orderId);
            if (optionalOrder.isPresent() && optionalOrder.get().getUserId() == user.getId()) {
                order lastOrder = optionalOrder.get();
                List<orderItem> orderItems = orderItemRepository.findByOrderId(lastOrder.getId());
                ModelAndView modelAndView = new ModelAndView("receiptPage");
                modelAndView.addObject("lastOrder", lastOrder);
                modelAndView.addObject("orderItems", orderItems);
                return modelAndView;
            }
        }
        return new ModelAndView("redirect:/errorPage");
    }

}
    
