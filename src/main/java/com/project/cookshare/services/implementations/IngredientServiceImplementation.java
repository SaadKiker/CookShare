package com.project.cookshare.services.implementations;

import com.project.cookshare.models.Ingredient;
import com.project.cookshare.repositories.IngredientRepository;
import com.project.cookshare.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//@Service
//public class IngredientServiceImplementation implements IngredientService {
//
//    private final IngredientRepository ingredientRepository;
//
//    @Autowired
//    public IngredientService(IngredientRepository ingredientRepository) {
//        this.ingredientRepository = ingredientRepository;
//    }
//
//    @Override
//    public Ingredient addIngredient(Ingredient ingredient) {
//        // Implementation
//    }
//
//    @Override
//    public Optional<Ingredient> getIngredientById(Integer id) {
//        // Implementation
//    }
//
//    @Override
//    public List<Ingredient> getAllIngredients() {
//        // Implementation
//    }
//
//    @Override
//    public Ingredient updateIngredient(Ingredient ingredient) {
//        // Implementation
//    }
//
//    @Override
//    public void deleteIngredientById(Integer id) {
//        // Implementation
//    }
//}
