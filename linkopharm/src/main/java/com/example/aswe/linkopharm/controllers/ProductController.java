package com.example.aswe.linkopharm.controllers;

import com.example.aswe.linkopharm.models.products;
import com.example.aswe.linkopharm.repositories.ProductRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public ModelAndView getProducts() {
        ModelAndView mav = new ModelAndView("displayproducts");
        mav.addObject("products", productRepository.findAll());
        return mav;
    }

    @GetMapping("/add")
    public ModelAndView addProductForm() {
        ModelAndView mav = new ModelAndView("addproducts");
        mav.addObject("product", new products()); 
        return mav;
    }

@PostMapping("/save")
public ModelAndView saveProduct(@Valid @ModelAttribute("product") products  product, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile) {
    ModelAndView mav = new ModelAndView();
    if (result.hasErrors()) {
        mav.setViewName("addproducts");
        mav.addObject("product", product);
        return mav; 
    }

    String contentType = imageFile.getContentType();
    if (!imageFile.isEmpty() && (contentType == null || !contentType.startsWith("image/"))) {
        mav.setViewName("addproducts");
        mav.addObject("product", product);
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
            product.setImagePath(fileName); 
        }
        productRepository.save(product);
        mav.setViewName("redirect:/products");
    } catch (IOException e) {
        mav.setViewName("addproducts");
        mav.addObject("product", product);
        mav.addObject("imageError", "Error saving image");
    }
    return mav;
}



    @GetMapping("/edit/{id}")
    public ModelAndView editProductForm(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("editproduct");
        products product = productRepository.findById(id).orElse(null);
        if (product != null) {
            mav.addObject("product", product);
        } else {
            return new ModelAndView("redirect:/products");
        }
        return mav;
    }

    @PostMapping("/update")
    public ModelAndView updateProduct(@Valid @ModelAttribute("product") products product, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("editproduct");
            mav.addObject("product", product);
            return mav; 
        }
        // Find the existing product
        products existingProduct = productRepository.findById(product.getId()).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setAvailability(product.getAvailability());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setCategory(product.getCategory());
            productRepository.save(existingProduct);
        }
        return new ModelAndView("redirect:/products");
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return "redirect:/products";
    }
}
