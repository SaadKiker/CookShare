package com.project.cookshare.repositories;

import com.project.cookshare.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Rating findByRecipeId(Integer recipeId);

}
