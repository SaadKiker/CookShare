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

    void addRecipe(Recipe recipe, int userId);
    void deleteRecipe(Integer recipeId);
    void saveRecipe(Recipe recipe, int userId);

    Recipe findRecipeById(Integer recipeId);
    RecipeDTO findRecipeByName(String recipeName);
    List<RecipeDTO> getAllRecipes();
    List<RecipeDTO> getRecipesByCategory(String category);
    List<RecipeDTO> getRecipesByAuthor(User author);
    List<RecipeDTO> getFavoriteRecipesByUser(Integer userId);
    List<RecipeDTO> searchByTitle(String title);

}
