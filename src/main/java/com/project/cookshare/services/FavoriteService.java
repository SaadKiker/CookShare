package com.project.cookshare.services;

import com.project.cookshare.DTOs.FavoriteDTO;
import com.project.cookshare.models.Favorite;
import java.util.List;
import java.util.Optional;

public interface FavoriteService {

    // Class Diagram Methods
    Favorite addFavorite(Integer recipeId);
    void removeFavoriteById(Integer recipeId);

    // Additional Methods
    List<Favorite> getAllFavorites();

}
