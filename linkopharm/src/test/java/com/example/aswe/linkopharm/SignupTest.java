package com.example.aswe.linkopharm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

import com.example.aswe.linkopharm.controllers.UserController;
import com.example.aswe.linkopharm.models.User;
import com.example.aswe.linkopharm.repositories.UserRepository;

@SpringBootTest
public class SignupTest {
    
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserSignup() {

        User newUser = new User();
        newUser.setFirstname("Eslam");
        newUser.setLastname("Ahmed");
        newUser.setUsername("ESS");
        newUser.setEmail("eslam@gmail.com");
        newUser.setPassword("Eslam@123");

        // mesh existing user 
        when(userRepository.findByEmail(newUser.getEmail())).thenReturn(null);
        when(userRepository.save(newUser)).thenReturn(newUser);

        // no validation errors
        BindingResult mockBindingResult = mock(BindingResult.class);
        when(mockBindingResult.hasErrors()).thenReturn(false);

        // Perform the signup
        ModelAndView mav = userController.saveFruit(newUser, mockBindingResult);

        // Verify that the ModelAndView is not null and redirects to the login page as expected
        assertNotNull(mav);
        assertEquals("redirect:/User/Login", mav.getViewName());
    }
}
