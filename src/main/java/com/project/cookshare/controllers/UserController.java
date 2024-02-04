package com.project.cookshare.controllers;

import com.project.cookshare.DTOs.UserDTO;
import com.project.cookshare.models.User;
import com.project.cookshare.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        // Use UserDTO instead of User for forms
        User user = new User(); // Assuming you have a default constructor
        model.addAttribute("user", user);
        return "login"; // Name of the template
    }
}
