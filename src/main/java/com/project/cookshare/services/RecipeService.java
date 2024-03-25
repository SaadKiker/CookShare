package com.project.cookshare.services;

import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.DTOs.InstructionStepDTO;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.DTOs.UserDTO;
import com.project.cookshare.models.InstructionStep;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.models.User;

import java.util.List;

public interface RecipeService {

    // Class Diagram Methods
    void addRecipe(Recipe recipe, int userId);
    void updateRecipe(RecipeDTO recipeDTO);
    void deleteRecipe(Integer recipeId);
    void saveRecipe(Recipe recipe, int userId);

    // Additional Methods
    Recipe findRecipeById(Integer recipeId);
    RecipeDTO findRecipeByName(String recipeName);
    List<RecipeDTO> getAllRecipes();
    List<InstructionStepDTO> getAllInstructions(String title);
    List<RecipeDTO> getRecipesByCategory(String category);
    List<RecipeDTO> getRecipesByAuthor(User author);


}
