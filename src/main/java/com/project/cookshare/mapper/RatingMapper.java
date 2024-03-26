package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.RatingDTO;
import com.project.cookshare.models.Rating;

public class RatingMapper {

    public static Rating mapToRatingEntity(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setId(ratingDTO.getId());
        rating.setRecipe(RecipeMapper.mapToRecipeEntity(ratingDTO.getRecipe()));
        return rating;
    }

    public static RatingDTO mapToRatingDTO(Rating rating) {
        return RatingDTO.builder()
                .id(rating.getId())
                .recipe(RecipeMapper.mapToRecipeDTO(rating.getRecipe()))
                .build();
    }

}
