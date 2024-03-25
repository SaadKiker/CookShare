package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.IngredientDTO;
import com.project.cookshare.mapper.IngredientMapper;
import com.project.cookshare.models.Ingredient;
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
    public void addIngredient(Ingredient ingredient) {
        ingredientRepository.save(ingredient);
    }

    @Override
    public void removeIngredientById(Integer ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }

    @Override
    public IngredientDTO findIngredientByName(String ingredientName) {
        Ingredient ingredient = ingredientRepository.findByName(ingredientName)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));
        return IngredientMapper.mapToIngredientDTO(ingredient);
    }

    @Override
    public IngredientDTO findIngredientById(Integer id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingredient not found"));
        return IngredientMapper.mapToIngredientDTO(ingredient);    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public List<Ingredient> FilterIngredients(String filter) {
        return ingredientRepository.findAll()
                .stream()
                .filter(ingredient -> ingredient.getName().contains(filter))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ingredient> findByRecipeId(Integer id) {
        return ingredientRepository.findByRecipeId(id);
    }
}
