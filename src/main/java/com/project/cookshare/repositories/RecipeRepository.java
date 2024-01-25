package com.project.cookshare.repositories;

import com.project.cookshare.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    // Additional query methods can be defined here
}
