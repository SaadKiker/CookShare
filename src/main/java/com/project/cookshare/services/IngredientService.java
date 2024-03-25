package com.project.cookshare.services;

import com.project.cookshare.DTOs.IngredientDTO;
import com.project.cookshare.models.Ingredient;
import java.util.List;
import java.util.Optional;

public interface IngredientService {

    // Class Diagram Methods
    void addIngredient(Ingredient ingredient);
    void removeIngredientById(Integer ingredientId);

    // Additional Methods
    IngredientDTO findIngredientByName(String ingredientName);
    IngredientDTO findIngredientById(Integer id);

    List<Ingredient> getAllIngredients();
    List<Ingredient> FilterIngredients(String filter);
    List<Ingredient> findByRecipeId(Integer Id);


}
