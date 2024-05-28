package com.example.aswe.linkopharm.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.aswe.linkopharm.repositories.UserRepository;

import com.example.aswe.linkopharm.models.User;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class dashboradController {
 @Autowired
    private UserRepository userRepository;

    private boolean isAdmin(HttpSession session) {
        String email = (String) session.getAttribute("email");
        if (email == null) {
            return false; 
        }
        User user = userRepository.findByEmail(email);
        return user != null && "admin".equals(user.getRole());
    }


@GetMapping("/dashboard")
public String dashboard(HttpSession session, HttpServletResponse response) throws IOException {
    if (!isAdmin(session)) {
        response.sendRedirect("/User/Login?error=access_denied"); 
        return null;
    }
    return "dashboard";
}

@GetMapping("/displayproducts")
public String displayproducts(HttpSession session, HttpServletResponse response) throws IOException {
    if (!isAdmin(session)) {
        response.sendRedirect("/User/Login?error=access_denied"); 
        return null;
    }
    return "displayproducts";
}

@GetMapping("/addproducts")
public String addproducts(HttpSession session, HttpServletResponse response) throws IOException {
    if (!isAdmin(session)) {
        response.sendRedirect("/User/Login?error=access_denied");
        return null;
    }
    return "addproducts";
}

}
