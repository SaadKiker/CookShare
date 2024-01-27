package com.project.cookshare.services;

import com.project.cookshare.DTOs.RatingDTO;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.models.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {

    // Class Diagram Methods
    void rateRecipe(Integer recipeId, RatingDTO ratingDTO);
    void updateRating(RecipeDTO recipeDTO, RatingDTO ratingDTO);
    void deleteRatingById(Integer ratingId);

    // Additional Methods
    List<Rating> getAllRatings();

}
