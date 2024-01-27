package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.RatingDTO;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.models.Rating;
import com.project.cookshare.repositories.RatingRepository;
import com.project.cookshare.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingServiceImplementation implements RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImplementation(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    // Class Diagram methods
    @Override
    public void rateRecipe(Integer recipeId, RatingDTO ratingDTO) {
        // This method would typically be in a RatingService, but if it's here:
        // TODO: Convert the ratingDTO to a Rating entity and save it
    }

    @Override
    public void updateRating(RecipeDTO recipeDTO, RatingDTO ratingDTO) {
        // Implementation
    }

    @Override
    public void deleteRatingById(Integer recipeId) {
        // Implementation
    }

    // Additional Methods
    @Override
    public List<Rating> getAllRatings() {
        return null;
    }


}
