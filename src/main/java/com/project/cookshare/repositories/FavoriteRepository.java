package com.project.cookshare.repositories;

import com.project.cookshare.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    boolean existsByRecipeIdAndUserId(Integer recipe_id, Integer user_id);
    List<Favorite> findByUserId(Integer userId);
    void deleteByRecipeIdAndUserId(Integer recipeId, Integer userId);
}
