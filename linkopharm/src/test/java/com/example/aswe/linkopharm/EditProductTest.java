package com.example.aswe.linkopharm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.controllers.ProductController;
import com.example.aswe.linkopharm.models.products;
import com.example.aswe.linkopharm.repositories.ProductRepository;

public class EditProductTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEditProduct() {
        products existingProduct = new products(1, "Original Name", "Yes", "100", "Original Description", "Original Category", null);
        products updatedProduct = new products(1, "Updated Name", "No", "200", "Updated Description", "Updated Category", null);
        
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(existingProduct));
        when(productRepository.save(any(products.class))).thenReturn(updatedProduct);
        when(bindingResult.hasErrors()).thenReturn(false); 

        ModelAndView mav = productController.updateProduct(updatedProduct, bindingResult);
        
        assertEquals("redirect:/products", mav.getViewName());
        verify(productRepository).save(any(products.class)); 
    }
}
