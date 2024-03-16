package com.example.aswe.linkopharm.controllers;

import com.example.aswe.linkopharm.models.products;
import com.example.aswe.linkopharm.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public ModelAndView saveProduct(@ModelAttribute products product, @RequestParam("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            try {
                // et2kd en directory exists
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Use file name
                String fileName = imageFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // khaly l image path in the product object to the filename
                product.setImagePath(fileName);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        }

        productRepository.save(product);
        return new ModelAndView("redirect:/products");
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
    public ModelAndView updateProduct(@ModelAttribute products product) {
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
