package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.RatingDTO;
import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.mapper.RatingMapper;
import com.project.cookshare.models.Rating;
import com.project.cookshare.repositories.RatingRepository;
import com.project.cookshare.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImplementation implements RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImplementation(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void rateRecipe(Integer recipeId, RatingDTO ratingDTO) {
        Rating rating = RatingMapper.mapToRatingEntity(ratingDTO);
        ratingRepository.save(rating);
    }

    @Override
    public void updateRating(RatingDTO ratingDTO) {
        Rating existingRating = ratingRepository.findById(ratingDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Rating not found"));
        RatingMapper.updateRatingEntityFromDTO(existingRating, ratingDTO);
        ratingRepository.save(existingRating);
    }

    @Override
    public void deleteRatingById(Integer ratingId) {
        ratingRepository.deleteById(ratingId);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
