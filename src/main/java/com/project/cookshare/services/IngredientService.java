package com.project.cookshare.services;

import com.project.cookshare.models.Ingredient;
import java.util.List;
import java.util.Optional;

public interface IngredientService {
    Ingredient addIngredient(Ingredient ingredient);
    Optional<Ingredient> getIngredientById(Integer id);
    List<Ingredient> getAllIngredients();
    Ingredient updateIngredient(Ingredient ingredient);
    void deleteIngredientById(Integer id);
}
