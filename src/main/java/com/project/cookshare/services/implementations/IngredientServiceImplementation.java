package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.IngredientDTO;
import com.project.cookshare.mapper.IngredientMapper;
import com.project.cookshare.mapper.RecipeMapper;
import com.project.cookshare.models.Ingredient;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.repositories.IngredientRepository;
import com.project.cookshare.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientServiceImplementation implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImplementation(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findByRecipeId(Integer id) {
        return ingredientRepository.findByRecipeId(id);
    }

}
