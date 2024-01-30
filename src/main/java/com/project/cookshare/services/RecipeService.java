package com.project.cookshare.services;

import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.models.Recipe;
import java.util.List;

public interface RecipeService {

    // Class Diagram Methods
    void addRecipe(RecipeDTO recipeDTO);
    void updateRecipe(RecipeDTO recipeDTO);
    void deleteRecipe(Integer recipeId);

    // Additional Methods
    RecipeDTO findRecipeById(Integer recipeId);
    List<RecipeDTO> getAllRecipes();

}
