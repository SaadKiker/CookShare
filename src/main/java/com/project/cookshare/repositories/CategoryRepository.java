package com.project.cookshare.repositories;

import com.project.cookshare.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO recipeCategory(recipe_id, category_id) VALUES (?1, ?2)", nativeQuery = true)
    void addRecipeToCategory(Integer recipeId, Integer categoryId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM recipeCategory WHERE recipe_id = ?1 AND category_id = ?2", nativeQuery = true)
    void removeRecipeFromCategory(Integer recipeId, Integer categoryId);
}
