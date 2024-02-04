package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.InstructionStepDTO;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.mapper.InstructionStepMapper;
import com.project.cookshare.mapper.RecipeMapper;
import com.project.cookshare.models.InstructionStep;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.repositories.InstructionStepRepository;
import com.project.cookshare.repositories.RecipeRepository;
import com.project.cookshare.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.cookshare.mapper.RecipeMapper.mapToRecipeDTO;

@Service
public class RecipeServiceImplementation implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final InstructionStepRepository instructionStepRepository;

    @Autowired
    public RecipeServiceImplementation(RecipeRepository recipeRepository, InstructionStepRepository instructionStepRepository) {
        this.recipeRepository = recipeRepository;
        this.instructionStepRepository = instructionStepRepository;
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
        return mapToRecipeDTO(recipe);
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
}
