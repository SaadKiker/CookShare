package com.project.cookshare.repositories;

import com.project.cookshare.models.Ingredient;
import com.project.cookshare.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Optional<Ingredient> findByName(String name);
    List<Ingredient> findByRecipe(Recipe recipe);
    Optional<Ingredient> findById(Integer id);
    List<Ingredient> findByRecipeId(Integer id);

}
