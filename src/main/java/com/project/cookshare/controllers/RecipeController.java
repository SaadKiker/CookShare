package com.project.cookshare.controllers;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.ui.Model;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RecipeController {
    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipes")
    public String listRecipes(Model model) {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        System.out.println(recipes);
        model.addAttribute("recipes", recipes);
        return "recipe-list";
    }
}
