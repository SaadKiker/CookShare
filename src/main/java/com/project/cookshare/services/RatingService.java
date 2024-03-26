package com.project.cookshare.services;

import com.project.cookshare.DTOs.RatingDTO;
import com.project.cookshare.models.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {

    // Class Diagram Methods
    Rating findRatingByRecipeId(Integer id);

    void rateRecipe(Integer recipeId, RatingDTO ratingDTO);

    Rating updateRating(Integer recipeId, String type);

    void deleteRatingById(Integer ratingId);

    // Additional Methods
    List<Rating> getAllRatings();

}
