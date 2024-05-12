package com.example.aswe.linkopharm.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.hibernate.mapping.List;
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

import com.example.aswe.linkopharm.models.cart;
import com.example.aswe.linkopharm.repositories.CartRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/cart")
public class cartController {

      private static final String UPLOAD_DIR = "linkopharm/src/main/resources/static/images/"; // Update with your upload directory path

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("")
public ModelAndView getProducts() {
    ModelAndView mav = new ModelAndView("cart");
    mav.addObject("cartItems", cartRepository.findAll());
    return mav;
}

    @GetMapping("/add")
    public ModelAndView addProductForm() {
        ModelAndView mav = new ModelAndView("addtocart");
        mav.addObject("cartItem", new cart()); // Assuming "addtocart.html" contains a form for adding to cart
        return mav;
    }

    @PostMapping("/save")
    public ModelAndView saveProduct(@Valid @ModelAttribute("cartItem") cart cartItem,
                                    BindingResult result,
                                    @RequestParam("imageFile") MultipartFile imageFile) {
        ModelAndView mav = new ModelAndView();
        if (result.hasErrors()) {
            mav.setViewName("addtocart");
            mav.addObject("cartItem", cartItem);
            return mav;
        }

        String contentType = imageFile.getContentType();
        if (!imageFile.isEmpty() && (contentType == null || !contentType.startsWith("image/"))) {
            mav.setViewName("addtocart");
            mav.addObject("cartItem", cartItem);
            mav.addObject("imageError", "File must be an image");
            return mav; 
        }
        try {
           if (!imageFile.isEmpty()) {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            String fileName = imageFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            cartItem.setProduct_image(fileName); 
        }
            cartRepository.save(cartItem);
            mav.setViewName("redirect:/cart");
        } catch (Exception e) {
            // Handle any exceptions that occur during saving
            mav.setViewName("addtocart");
            mav.addObject("cartItem", cartItem);
            mav.addObject("error", "Error saving cart item");
        }
        return mav;
    }
}
