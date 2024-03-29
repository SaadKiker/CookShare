package com.project.cookshare.services;

import com.project.cookshare.DTOs.FavoriteDTO;
import com.project.cookshare.models.Favorite;
import java.util.List;
import java.util.Optional;

public interface FavoriteService {

    boolean addFavorite(Integer recipeId, Integer userId);
    void removeFavorite(Integer recipeId, Integer userId);
    boolean isFavorite(int recipeId, int userId);

}
