package com.project.cookshare.repositories;

import com.project.cookshare.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    // Additional query methods can be defined here
}
