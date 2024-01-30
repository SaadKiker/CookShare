package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.models.User; // Import User if needed for author mapping

public class RecipeMapper {

    public static Recipe mapToRecipeEntity(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDTO.getId());
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setInstructions(recipeDTO.getInstructions());
        recipe.setCreated_date(recipeDTO.getCreated_date());
        recipe.setCooking_time(recipeDTO.getCooking_time());
        recipe.setImage(recipeDTO.getImage());

        User author = UserMapper.mapToUserEntity(recipeDTO.getAuthor());
        recipe.setAuthor(author);

        return recipe;
    }

    public static RecipeDTO mapToRecipeDTO(Recipe recipe) {
        RecipeDTO recipeDTO = RecipeDTO.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .instructions(recipe.getInstructions())
                .created_date(recipe.getCreated_date())
                .cooking_time(recipe.getCooking_time())
                .image(recipe.getImage())
                .build();

        recipeDTO.setAuthor(UserMapper.mapToUserDTO(recipe.getAuthor()));

        return recipeDTO;
    }

    public static void updateRecipeEntityFromDTO(Recipe existingRecipe, RecipeDTO recipeDTO) {
        existingRecipe.setTitle(recipeDTO.getTitle());
        existingRecipe.setDescription(recipeDTO.getDescription());
        existingRecipe.setInstructions(recipeDTO.getInstructions());
        existingRecipe.setCreated_date(recipeDTO.getCreated_date());
        existingRecipe.setCooking_time(recipeDTO.getCooking_time());
        existingRecipe.setImage(recipeDTO.getImage());

        existingRecipe.setAuthor(UserMapper.mapToUserEntity(recipeDTO.getAuthor()));

    }

}