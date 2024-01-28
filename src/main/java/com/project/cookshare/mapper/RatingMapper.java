package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.RatingDTO;
import com.project.cookshare.models.Rating;

public class RatingMapper {

    public static Rating mapToRatingEntity(RatingDTO ratingDTO) {
        Rating rating = new Rating();
        rating.setId(ratingDTO.getId());
        rating.setScore(ratingDTO.getScore());
        rating.setRecipe(RecipeMapper.mapToRecipeEntity(ratingDTO.getRecipe()));
        rating.setRater(UserMapper.mapToUserEntity(ratingDTO.getRater()));
        return rating;
    }

    public static RatingDTO mapToRatingDTO(Rating rating) {
        return RatingDTO.builder()
                .id(rating.getId())
                .score(rating.getScore())
                .recipe(RecipeMapper.mapToRecipeDTO(rating.getRecipe()))
                .rater(UserMapper.mapToUserDTO(rating.getRater()))
                .build();
    }

    public static void updateRatingEntityFromDTO(Rating existingRating, RatingDTO ratingDTO) {
        existingRating.setScore(ratingDTO.getScore());
    }
}
