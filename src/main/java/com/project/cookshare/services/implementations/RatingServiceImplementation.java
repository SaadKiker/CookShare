package com.project.cookshare.services.implementations;

import com.project.cookshare.models.Rating;
import com.project.cookshare.repositories.RatingRepository;
import com.project.cookshare.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//@Service
//public class RatingServiceImplementation implements RatingService {
//
//    private final RatingRepository ratingRepository;
//
//    @Autowired
//    public RatingService(RatingRepository ratingRepository) {
//        this.ratingRepository = ratingRepository;
//    }
//
//    @Override
//    public Rating addRating(Rating rating) {
//        // Implementation
//    }
//
//    @Override
//    public Optional<Rating> getRatingById(Integer id) {
//        // Implementation
//    }
//
//    @Override
//    public List<Rating> getAllRatings() {
//        // Implementation
//    }
//
//    @Override
//    public Rating updateRating(Rating rating) {
//        // Implementation
//    }
//
//    @Override
//    public void deleteRatingById(Integer id) {
//        // Implementation
//    }
//}
