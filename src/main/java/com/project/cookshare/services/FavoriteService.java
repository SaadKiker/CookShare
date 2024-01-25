package com.project.cookshare.services;

import com.project.cookshare.models.Favorite;
import java.util.List;
import java.util.Optional;

public interface FavoriteService {
    Favorite addFavorite(Favorite favorite);
    Optional<Favorite> getFavoriteById(Integer id);
    List<Favorite> getAllFavorites();
    void deleteFavoriteById(Integer id);
}
