package com.project.cookshare.controllers;

import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.DTOs.UserDTO;
import com.project.cookshare.mapper.UserMapper;
import com.project.cookshare.models.User;
import com.project.cookshare.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        UserDTO existingUserDTO = userService.findUserByUsername(user.getUsername());
        if (existingUserDTO != null && existingUserDTO.getPassword().equals(user.getPassword())) {
            User existingUser = UserMapper.mapToUserEntity(existingUserDTO);
            session.setAttribute("user", existingUser);
            return "redirect:/recipes"; // Redirect to a home page or dashboard
        } else {
            model.addAttribute("loginError", "Invalid username or password.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(SessionStatus status, HttpSession session) {
        status.setComplete(); // Clears @SessionAttributes
        session.invalidate(); // Invalidate session
        return "redirect:/login";
    }
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // Correctly call the method with the user's ID
            int recipesSubmittedCount = userService.calculateRecipesSubmittedByUser(user.getId());
            UserDTO userDTO = userService.findUserByUsername(user.getUsername());
            // It might be more appropriate to add this count to the UserDTO or directly to the model
            userDTO.setRecipesSubmitted(recipesSubmittedCount); // Assuming UserDTO has this field
            model.addAttribute("userDTO", userDTO);
        } else {
            // Handle case where user is not logged in or session has expired
            return "redirect:/login";
        }
        return "my_profile";
    }



    @GetMapping("/contact")
    public String contact(String username, Model model) {
//        UserDTO userDTO = userService.findUserByUsername(username);
//        model.addAttribute("user", userDTO);
        return "contact";
    }
}
