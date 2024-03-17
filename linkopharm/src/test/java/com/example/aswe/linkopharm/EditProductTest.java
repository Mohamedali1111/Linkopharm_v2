package com.example.aswe.linkopharm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.example.aswe.linkopharm.controllers.ProductController;
import com.example.aswe.linkopharm.models.products;
import com.example.aswe.linkopharm.repositories.ProductRepository;

public class EditProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testEditProduct() {
        //  product
        products existingProduct = new products(1, "Original Name", "Yes", "100", "Original Description", "Original Category");
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(existingProduct));

        // lma a3ml update 
        products updatedProduct = new products(1, "Updated Name", "No", "200", "Updated Description", "Updated Category");
        when(productRepository.save(any(products.class))).thenReturn(updatedProduct);

        ModelAndView mav = productController.updateProduct(updatedProduct);

        //updated product reflected
        assertEquals("redirect:/products", mav.getViewName());
        verify(productRepository).save(updatedProduct); 
    }
}
