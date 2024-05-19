package com.example.aswe.linkopharm.controllers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
import com.example.aswe.linkopharm.models.cart;
import com.example.aswe.linkopharm.models.order;
import com.example.aswe.linkopharm.models.orderItem;
import com.example.aswe.linkopharm.models.products;
import com.example.aswe.linkopharm.repositories.CartRepository;
import com.example.aswe.linkopharm.repositories.ProductRepository;
import com.example.aswe.linkopharm.repositories.UserRepository;
import com.example.aswe.linkopharm.repositories.orderItemRepository;
import com.example.aswe.linkopharm.repositories.orderRepository;

import jakarta.servlet.http.HttpSession;



@RestController 
@RequestMapping("/checkout")
public class checkoutController {
    
    @Autowired
    private orderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private orderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;


    @GetMapping("")
    public ModelAndView checkout(HttpSession session) {
        ModelAndView mav = new ModelAndView("checkout"); 
        mav.addObject("Order", new order());
        String email = (String) session.getAttribute("email");
          List<cart> cartItems = new ArrayList<cart>();

        if (email == null) {
            mav.addObject("cartItems", null);
            mav.addObject("totalPrice", BigDecimal.ZERO);
            mav.addObject("totalQuantity", 0);
        } else {
            User user = userRepository.findByEmail(email);
            cartItems = cartRepository.findByUserId(user.getId());
            int totalQuantity = calculateTotalQuantity(cartItems);
            BigDecimal totalPrice = calculateTotalPrice(cartItems);
            if (user != null && totalQuantity != 0) {
                mav.addObject("cartItems", cartItems);
                mav.addObject("totalPrice", totalPrice);
                mav.addObject("totalQuantity", totalQuantity);
            }
            else{
                mav.addObject("cartItems", null);
                mav.addObject("totalPrice", totalPrice);
                mav.addObject("totalQuantity", totalQuantity);
            }
        }
        if(cartItems.isEmpty()){
             return new ModelAndView("redirect:/errorPage/emptyCart");
            
        }
        return mav;
    }


    @PostMapping("/placeOrder")
    public ModelAndView addOrder(@ModelAttribute order order, HttpSession session) {
        String userEmail = (String) session.getAttribute("email");
        if (userEmail == null) {
            return new ModelAndView("redirect:/User/Login?error=user_not_found");
        }
    
        User user = this.userRepository.findByEmail(userEmail);
        if (user == null) {
            return new ModelAndView("redirect:/User/Login?error=user_not_found");
        }
    
        List<cart> cartItems = cartRepository.findByUserId(user.getId());
        int totalQuantity = calculateTotalQuantity(cartItems);
    
        if (totalQuantity == 0) {
            return new ModelAndView("redirect:/errorPage/emptyCart");
        }
    
        order.setUserId(user.getId());
        order.setStatus("Pending");
        order.setTotalPrice(calculateTotalPrice(cartItems));
        order.setOrderDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    
        this.orderRepository.save(order);
    
        for (cart item : cartItems) {
            Optional<products> optionalProduct = this.productRepository.findById(item.getProductId());
            if (optionalProduct.isPresent()) {
                orderItem orderItem = new orderItem();
                orderItem.setOrder(order);
                orderItem.setProduct(optionalProduct.get());
                orderItem.setQuantity(item.getQuantity());
                this.orderItemRepository.save(orderItem);
            }
        }
        
        this.cartRepository.deleteAll(cartItems);
        return new ModelAndView("redirect:/checkout/confirmationPage");
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

    
    public BigDecimal calculateTotalPrice(List<cart> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (cart item : cartItems) {
            BigDecimal price = BigDecimal.valueOf(item.getProduct_price());
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            BigDecimal itemTotal = price.multiply(quantity);
            total = total.add(itemTotal);
        }
        return total;
    }
    
    public int calculateTotalQuantity(List<cart> cartItems) {
        int totalQuantity = 0;
        for (cart item : cartItems) {
            totalQuantity += item.getQuantity();
        }
        return totalQuantity;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("message", "An error occurred: " + e.getMessage());
        return mav;
    }
}











