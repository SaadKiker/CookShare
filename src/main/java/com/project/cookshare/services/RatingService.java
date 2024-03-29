package com.project.cookshare.services;

import com.project.cookshare.DTOs.RatingDTO;
import com.project.cookshare.models.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {

    Rating findRatingByRecipeId(Integer id);
    Rating updateRating(Integer recipeId, String type);

}
