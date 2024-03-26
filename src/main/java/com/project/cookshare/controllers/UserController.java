package com.project.cookshare.controllers;

import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.DTOs.UserDTO;
import com.project.cookshare.mapper.UserMapper;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.models.User;
import com.project.cookshare.services.RecipeService;
import com.project.cookshare.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    private final UserService userService;
    private final RecipeService recipeService;

    @Autowired
    public UserController(UserService userService, RecipeService recipeService) {
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpSession session) {
        User existingUser = userService.findUserByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
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
    public String profile( HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            // Correctly call the method with the user's ID
            int recipesSubmittedCount = userService.calculateRecipesSubmittedByUser(user.getId());
            User userDTO = userService.findUserByUsername(user.getUsername());
            // It might be more appropriate to add this count to the UserDTO or directly to the model
            userDTO.setRecipesSubmitted(recipesSubmittedCount); // Assuming UserDTO has this field
            model.addAttribute("userDTO", userDTO);
            // Assuming you have a method that fetches the current user based on the login
            // Fetch the recipes that are created by the logged-in user
            List<RecipeDTO> recipes = recipeService.getRecipesByAuthor(user);

            model.addAttribute("recipes", recipes);
        } else {
            // Handle case where user is not logged in or session has expired
            return "redirect:/login";
        }
        return "my_profile";
    }

    @PostMapping("/deleteRecipe/{id}")
    public String deleteRecipe(@PathVariable("id") int recipeId, HttpSession session, RedirectAttributes attributes) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // User is not logged in, redirect to login page
            attributes.addFlashAttribute("error", "You must be logged in to delete recipes.");
            return "redirect:/login";
        }

        // Optional: Check if the recipe belongs to the user
        Recipe recipe = recipeService.findRecipeById(recipeId);
        if (recipe == null || !recipe.getAuthor().getId().equals(user.getId())) {
            // Recipe does not exist or does not belong to the logged-in user
            attributes.addFlashAttribute("error", "You do not have permission to delete this recipe.");
            return "redirect:/profile";
        }

        // Delete the recipe
        recipeService.deleteRecipe(recipeId);

        // Add a success message and redirect back to the user profile
        attributes.addFlashAttribute("message", "Recipe successfully deleted.");
        return "redirect:/profile";
    }

    @GetMapping("/contact")
    public String contact(String username, Model model) {
//        UserDTO userDTO = userService.findUserByUsername(username);
//        model.addAttribute("user", userDTO);
        return "contact";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserDTO userDTO, Model model, HttpSession session) {
            userService.registerUser(userDTO); // Assuming you have a method to register and check if successful
            session.setAttribute("user", UserMapper.mapToUserEntity(userDTO)); // Optionally add the new user to the session
            return "login"; // Redirect them to the login page (or a welcome page)
        }
    }

