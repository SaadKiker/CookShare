package com.project.cookshare.services.implementations;

import com.project.cookshare.models.Favorite;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.models.User;
import com.project.cookshare.repositories.FavoriteRepository;
import com.project.cookshare.repositories.RecipeRepository;
import com.project.cookshare.services.FavoriteService;
import com.project.cookshare.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImplementation implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RecipeRepository recipeRepository;
    private final UserService userService;

    @Autowired
    public FavoriteServiceImplementation(FavoriteRepository favoriteRepository,
                                         RecipeRepository recipeRepository, UserService userService) {
        this.favoriteRepository = favoriteRepository;
        this.recipeRepository = recipeRepository;
        this.userService = userService;
    }

    @Override
    public boolean addFavorite(Integer recipeId, Integer userId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
        Favorite favorite = new Favorite();
        User user = userService.findUserById(userId);
        favorite.setUser(user);
        favorite.setRecipe(recipe);
        favoriteRepository.save(favorite);
        return true;
    }

    @Override
    @Transactional
    public void removeFavorite(Integer recipeId, Integer userId) {
        favoriteRepository.deleteByRecipeIdAndUserId(recipeId, userId);

    }

    @Override
    public boolean isFavorite(int recipeId, int userId) {
        return favoriteRepository.existsByRecipeIdAndUserId(recipeId, userId);
    }

    @Override
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }
}
