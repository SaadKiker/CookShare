package com.project.cookshare.repositories;

import com.project.cookshare.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {
    // Additional query methods can be defined here
}
