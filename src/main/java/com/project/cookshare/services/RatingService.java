package com.project.cookshare.services;

import com.project.cookshare.models.Rating;
import java.util.List;
import java.util.Optional;

public interface RatingService {
    Rating addRating(Rating rating);
    Optional<Rating> getRatingById(Integer id);
    List<Rating> getAllRatings();
    Rating updateRating(Rating rating);
    void deleteRatingById(Integer id);
}
