package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.RatingDTO;
import com.project.cookshare.mapper.RatingMapper;
import com.project.cookshare.models.Rating;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.repositories.RatingRepository;
import com.project.cookshare.services.RatingService;
import com.project.cookshare.services.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImplementation implements RatingService {

    private final RatingRepository ratingRepository;
    private final RecipeService recipeService;

    @Autowired
    public RatingServiceImplementation(RatingRepository ratingRepository, RecipeService recipeService) {
        this.ratingRepository = ratingRepository;
        this.recipeService = recipeService;
    }

    @Override
    public Rating findRatingByRecipeId(Integer id) {
        return ratingRepository.findByRecipeId(id);
    }


    @Override
    public Rating updateRating(Integer recipeId, String type) {
        Recipe recipe = recipeService.findRecipeById(recipeId);
        if (recipe == null) {
            throw new EntityNotFoundException("Recipe not found with id: " + recipeId);
        }

        Rating rating = ratingRepository.findByRecipeId(recipeId);
        if (rating == null) {
            rating = new Rating();
            rating.setRecipe(recipe);
            rating.setLikes(0);
            rating.setDislikes(0);
        }

        if ("like".equals(type)) {
            rating.setLikes(rating.getLikes() + 1);
        } else if ("dislike".equals(type)) {
            rating.setDislikes(rating.getDislikes() + 1);
        }

        return ratingRepository.save(rating);
    }

}
