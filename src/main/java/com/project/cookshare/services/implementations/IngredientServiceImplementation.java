package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.IngredientDTO;
import com.project.cookshare.models.Ingredient;
import com.project.cookshare.repositories.IngredientRepository;
import com.project.cookshare.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImplementation implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImplementation(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // Class diagram Methods
    @Override
    public void addIngredient(Ingredient ingredient) {
        // Implementation
    }

    @Override
    public void removeIngredientById(Integer ingredientId) {

    }

    @Override
    public IngredientDTO findIngredientByName(String ingredientName) {
        return null;
    }

    // Additional Methods
    @Override
    public List<Ingredient> getAllIngredients() {
        return null;
    }

}
