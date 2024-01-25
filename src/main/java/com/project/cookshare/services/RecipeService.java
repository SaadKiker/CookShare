package com.project.cookshare.services;

import com.project.cookshare.models.Recipe;
import java.util.List;
import java.util.Optional;

public interface RecipeService {
    Recipe addRecipe(Recipe recipe);
    Optional<Recipe> getRecipeById(Integer id);
    List<Recipe> getAllRecipes();
    Recipe updateRecipe(Recipe recipe);
    void deleteRecipeById(Integer id);
}
