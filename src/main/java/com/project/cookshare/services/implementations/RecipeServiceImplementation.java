package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.mapper.RecipeMapper;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.repositories.RecipeRepository;
import com.project.cookshare.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecipeServiceImplementation implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImplementation(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void addRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = RecipeMapper.mapToRecipeEntity(recipeDTO);
        recipeRepository.save(recipe);
    }

    @Override
    public void updateRecipe(RecipeDTO recipeDTO) {
        Recipe existingRecipe = recipeRepository.findById(recipeDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));

        RecipeMapper.updateRecipeEntityFromDTO(existingRecipe, recipeDTO);

        recipeRepository.save(existingRecipe);
    }


    @Override
    public void deleteRecipe(Integer recipeId) {
        recipeRepository.deleteById(recipeId);
    }

    @Override
    public RecipeDTO findRecipeById(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
        return RecipeMapper.mapToRecipeDTO(recipe);
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
}
