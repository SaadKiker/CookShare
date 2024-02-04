package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.InstructionStepDTO;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.models.Category;
import com.project.cookshare.models.InstructionStep;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.models.User; // Import User if needed for author mapping

import java.util.HashSet;
import java.util.Set;

public class RecipeMapper {

    public static Recipe mapToRecipeEntity(RecipeDTO recipeDTO) {
        Recipe recipe = new Recipe();
        recipe.setId(recipeDTO.getId());
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setCreated_date(recipeDTO.getCreated_date());
        recipe.setCooking_time(recipeDTO.getCooking_time());
        recipe.setImage(recipeDTO.getImage());

        Category category = CategoryMapper.mapToCategoryEntity(recipeDTO.getCategory());
        recipe.setCategory(category);
        User author = UserMapper.mapToUserEntity(recipeDTO.getAuthor());
        recipe.setAuthor(author);

        return recipe;
    }

    public static RecipeDTO mapToRecipeDTO(Recipe recipe) {

        return RecipeDTO.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .created_date(recipe.getCreated_date())
                .cooking_time(recipe.getCooking_time())
                .image(recipe.getImage())
                .author(UserMapper.mapToUserDTO(recipe.getAuthor()))
                .category(CategoryMapper.mapToCategoryDTO(recipe.getCategory()))
                .build();
    }

    public static void updateRecipeEntityFromDTO(Recipe existingRecipe, RecipeDTO recipeDTO) {
        existingRecipe.setTitle(recipeDTO.getTitle());
        existingRecipe.setDescription(recipeDTO.getDescription());
        existingRecipe.setCreated_date(recipeDTO.getCreated_date());
        existingRecipe.setCooking_time(recipeDTO.getCooking_time());
        existingRecipe.setImage(recipeDTO.getImage());
        existingRecipe.setCategory(CategoryMapper.mapToCategoryEntity(recipeDTO.getCategory()));
        existingRecipe.setAuthor(UserMapper.mapToUserEntity(recipeDTO.getAuthor()));
    }
}


