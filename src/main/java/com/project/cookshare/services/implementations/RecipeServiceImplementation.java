package com.project.cookshare.services.implementations;

import com.project.cookshare.models.Recipe;
import com.project.cookshare.repositories.RecipeRepository;
import com.project.cookshare.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//@Service
//public class RecipeServiceImplementation implements RecipeService {
//
//    private final RecipeRepository recipeRepository;
//
//    @Autowired
//    public RecipeService(RecipeRepository recipeRepository) {
//        this.recipeRepository = recipeRepository;
//    }
//
//    @Override
//    public Recipe addRecipe(Recipe recipe) {
//        // Implementation
//    }
//
//    @Override
//    public Optional<Recipe> getRecipeById(Integer id) {
//        // Implementation
//    }
//
//    @Override
//    public List<Recipe> getAllRecipes() {
//        // Implementation
//    }
//
//    @Override
//    public Recipe updateRecipe(Recipe recipe) {
//        // Implementation
//    }
//
//    @Override
//    public void deleteRecipeById(Integer id) {
//        // Implementation
//    }
//}
