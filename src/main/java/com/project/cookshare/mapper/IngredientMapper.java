package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.IngredientDTO;
import com.project.cookshare.models.Ingredient;

public class IngredientMapper {

    public static Ingredient mapToIngredientEntity(IngredientDTO ingredientDTO) {
        Ingredient ingredient = new Ingredient();
        ingredient.setId(ingredientDTO.getId());
        ingredient.setName(ingredientDTO.getName());
        ingredient.setQuantity(ingredientDTO.getQuantity());
        return ingredient;
    }

    public static IngredientDTO mapToIngredientDTO(Ingredient ingredient) {
        return IngredientDTO.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .quantity(ingredient.getQuantity())
                .build();
    }
}
