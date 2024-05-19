package com.example.aswe.linkopharm.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.aswe.linkopharm.models.User;
import com.example.aswe.linkopharm.models.cart;
import com.example.aswe.linkopharm.repositories.CartRepository;
import com.example.aswe.linkopharm.repositories.UserRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/cart")
public class cartController {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public ModelAndView getProducts(HttpSession session) {
        ModelAndView mav = new ModelAndView("cart");
        String email = (String) session.getAttribute("email");
        if (email == null) {
            mav.addObject("cartItems", Collections.emptyList());
            mav.addObject("totalPrice", BigDecimal.ZERO);
            mav.addObject("totalQuantity", 0);
        } else {
            User user = userRepository.findByEmail(email);
            if (user != null) {
                List<cart> cartItems = cartRepository.findByUserId(user.getId());
                mav.addObject("cartItems", cartItems);
                BigDecimal totalPrice = calculateTotalPrice(cartItems);
                int totalQuantity = calculateTotalQuantity(cartItems);
                mav.addObject("totalPrice", totalPrice); // Add total price to the model
                mav.addObject("totalQuantity", totalQuantity); // Add total quantity to the model
            }
        }
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView showAddToCartForm(HttpSession session) {
        ModelAndView mav = new ModelAndView("cart.html");
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return new ModelAndView("redirect:/User/Login");
        }
        cart newItem = new cart();
        mav.addObject("cartItem", newItem);
        return mav;
    }

  @PostMapping("/add")
public ModelAndView addToCart(@ModelAttribute("cartItem") cart newItem, HttpSession session, RedirectAttributes redirectAttributes) {
    String email = (String) session.getAttribute("email");
    if (email == null) {
        return new ModelAndView("redirect:/User/Login");
    }
    User user = userRepository.findByEmail(email);
    if (user != null) {
        List<cart> existingItems = cartRepository.findByUserId(user.getId()).stream()
            .filter(item -> item.getProduct_name().equals(newItem.getProduct_name()))
            .collect(Collectors.toList());

        if (!existingItems.isEmpty()) {
            // Product is already in cart
            redirectAttributes.addFlashAttribute("errorMessage", "Product already in cart");
            return new ModelAndView("redirect:/productsPage");
        } else {
            // Add new item to cart
            newItem.setUser(user);
            cartRepository.save(newItem);
            redirectAttributes.addFlashAttribute("successMessage", "Product added to cart successfully");
        }
    }
    return new ModelAndView("redirect:/productsPage");
}

    @PostMapping("/remove")
public String removeItemFromCart(@RequestParam("id") int cartId, RedirectAttributes redirectAttributes) {
    cartRepository.deleteById(cartId);
    redirectAttributes.addFlashAttribute("successMessage", "Item removed successfully!");
    return "redirect:/cart";
}
@PostMapping("/updateQuantity")
public String updateCartItemQuantity(@RequestParam("id") int cartId, @RequestParam("operation") String operation) {
    Optional<cart> cart = cartRepository.findById(cartId);
    if (cart.isPresent()) {
        cart cartItem = cart.get();
        int currentQuantity = cartItem.getQuantity();
        if ("increase".equals(operation)) {
            cartItem.setQuantity(currentQuantity + 1);
        } else if ("decrease".equals(operation) && currentQuantity > 1) {
            cartItem.setQuantity(currentQuantity - 1);
        }
        cartRepository.save(cartItem);
    }
    return "redirect:/cart";
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
}
