package com.project.cookshare.controllers;

import com.project.cookshare.DTOs.InstructionStepDTO;
import com.project.cookshare.mapper.InstructionStepMapper;
import com.project.cookshare.mapper.RecipeMapper;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.repositories.InstructionStepRepository;
import org.springframework.ui.Model;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final InstructionStepRepository instructionStepRepository;

    @Autowired
    public RecipeController(RecipeService recipeService, InstructionStepRepository instructionStepRepository) {
        this.recipeService = recipeService;
        this.instructionStepRepository = instructionStepRepository;
    }

    @GetMapping("/recipes")
    public String listRecipes(Model model) {
        List<RecipeDTO> recipes = recipeService.getAllRecipes();
        System.out.println(recipes);
        model.addAttribute("recipes", recipes);
        return "recipes";
    }

    @GetMapping("/recipe/{title}")
    public String getRecipeByName(@PathVariable("title") String title, Model model) {
        RecipeDTO recipeDTO = recipeService.findRecipeByName(title);
        Recipe recipe = RecipeMapper.mapToRecipeEntity(recipeDTO);
        List<InstructionStepDTO> instructionSteps = instructionStepRepository.findByRecipe(recipe).stream()
                .map(InstructionStepMapper::mapToInstructionStepDTO) // Assuming you have this mapper method
                .collect(Collectors.toList());
        model.addAttribute("recipe", recipeDTO);
        model.addAttribute("instructionSteps", instructionSteps);
        return "recipe";
    }

}
