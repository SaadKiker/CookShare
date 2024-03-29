package com.project.cookshare.services;

import com.project.cookshare.DTOs.IngredientDTO;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.models.Ingredient;
import java.util.List;
import java.util.Optional;

public interface IngredientService {

    List<Ingredient> findByRecipeId(Integer Id);

}
