package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.InstructionStepDTO;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.mapper.InstructionStepMapper;
import com.project.cookshare.mapper.RecipeMapper;
import com.project.cookshare.models.Favorite;
import com.project.cookshare.models.InstructionStep;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.models.User;
import com.project.cookshare.repositories.FavoriteRepository;
import com.project.cookshare.repositories.InstructionStepRepository;
import com.project.cookshare.repositories.RecipeRepository;
import com.project.cookshare.repositories.UserRepository;
import com.project.cookshare.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.cookshare.mapper.RecipeMapper.mapToRecipeDTO;

@Service
public class RecipeServiceImplementation implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final InstructionStepRepository instructionStepRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;

    @Autowired
    public RecipeServiceImplementation(RecipeRepository recipeRepository, InstructionStepRepository instructionStepRepository, UserRepository userRepository, FavoriteRepository favoriteRepository) {
        this.recipeRepository = recipeRepository;
        this.instructionStepRepository = instructionStepRepository;
        this.userRepository = userRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void addRecipe(Recipe recipe, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        recipe.setAuthor(user); // Assuming you have a method to set the recipe's author
        user.setRecipesSubmitted(user.getRecipesSubmitted() + 1);
        userRepository.save(user);
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
    public void saveRecipe(Recipe recipe, int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        recipe.setAuthor(user); // Assuming you have a method to set the recipe's author
        recipeRepository.save(recipe);
        userRepository.save(user);

    }

    @Override
    public Recipe findRecipeById(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
        return recipe;
    }

    @Override
    public RecipeDTO findRecipeByName(String title) {
        Recipe recipe = recipeRepository.findByTitle(title);
        return mapToRecipeDTO(recipe);
    }

    @Override
    public List<RecipeDTO> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream().map(RecipeMapper::mapToRecipeDTO).collect(Collectors.toList());

    }

    @Override
    public List<InstructionStepDTO> getAllInstructions(String title) {
        Recipe recipe = recipeRepository.findByTitle(title);
        List<InstructionStep> instructions = instructionStepRepository.findByRecipe(recipe);
        return instructions.stream().map(InstructionStepMapper::mapToInstructionStepDTO).collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTO> getRecipesByCategory(String categoryName) {
        // Fetch recipes by category name
        List<Recipe> recipes = recipeRepository.findByCategoryName(categoryName);
        // Use the mapper to convert each Recipe entity to a RecipeDTO
        return recipes.stream()
                .map(RecipeMapper::mapToRecipeDTO) // Assuming the method to convert to DTO is called toDto
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTO> getRecipesByAuthor(User author) {
        List<Recipe> recipes = recipeRepository.findByAuthor(author);
        // Use the mapper to convert each Recipe entity to a RecipeDTO
        return recipes.stream()
                .map(RecipeMapper::mapToRecipeDTO) // Assuming the method to convert to DTO is called toDto
                .collect(Collectors.toList());
    }

    @Override
    public List<RecipeDTO> getFavoriteRecipesByUser(Integer userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);
        List<RecipeDTO> favoriteRecipes = new ArrayList<>();

        for (Favorite favorite : favorites) {
            // Assuming you have a method to convert Recipe entity to RecipeDTO
            Recipe recipe = recipeRepository.findById(favorite.getRecipe().getId()).orElse(null);
            if (recipe != null) {
                RecipeDTO dto = mapToRecipeDTO(recipe); // Implement this method based on your DTO
                favoriteRecipes.add(dto);
            }
        }
        return favoriteRecipes;
    }
}
