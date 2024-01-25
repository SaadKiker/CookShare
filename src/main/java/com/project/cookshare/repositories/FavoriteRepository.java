package com.project.cookshare.repositories;

import com.project.cookshare.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    // Additional query methods can be defined here
}
