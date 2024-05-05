package com.example.aswe.linkopharm.controllers;

import com.example.aswe.linkopharm.models.order;
import com.example.aswe.linkopharm.repositories.orderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class orderController {

    @Autowired
    private orderRepository orderRepository;

    @GetMapping("")
    public ModelAndView getAllOrders() {
        List<order> orders = orderRepository.findAll();
        ModelAndView mav = new ModelAndView("orderdash");
        mav.addObject("orders", orders);
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addOrderForm() {
        ModelAndView mav = new ModelAndView("addorder");
        mav.addObject("order", new order());
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView saveOrder(@ModelAttribute("order") order order, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("addorder");
            mav.addObject("order", order);
            return mav;
        }
        orderRepository.save(order);
        return new ModelAndView("redirect:/orders");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editOrderForm(@PathVariable("id") int id) {
        ModelAndView mav = new ModelAndView("editorder");
        Optional<order> optionalOrder = orderRepository.findById(id);
        optionalOrder.ifPresent(o -> mav.addObject("order", o));
        return mav;
    }

    @PostMapping("/update/{id}")
    public ModelAndView updateOrder(@PathVariable("id") int id, @ModelAttribute("order") order updatedOrder, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("editorder");
            mav.addObject("order", updatedOrder);
            return mav;
        }
        updatedOrder.setId(id); 
        orderRepository.save(updatedOrder);
        return new ModelAndView("redirect:/orders");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteOrder(@PathVariable("id") int id) {
        orderRepository.deleteById(id);
        return new ModelAndView("redirect:/orders");
    }
}
