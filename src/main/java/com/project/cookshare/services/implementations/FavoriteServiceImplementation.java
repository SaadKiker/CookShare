package com.project.cookshare.services.implementations;

import com.project.cookshare.models.Favorite;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.repositories.FavoriteRepository;
import com.project.cookshare.repositories.RecipeRepository;
import com.project.cookshare.services.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImplementation implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public FavoriteServiceImplementation(FavoriteRepository favoriteRepository,
                                         RecipeRepository recipeRepository) {
        this.favoriteRepository = favoriteRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Favorite addFavorite(Integer recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
        Favorite favorite = new Favorite();
        favorite.setUser(recipe.getAuthor());
        favorite.setRecipe(recipe);
        return favoriteRepository.save(favorite);
    }

    @Override
    public void removeFavoriteById(Integer recipeId) {
        favoriteRepository.deleteById(recipeId);
    }

    @Override
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }
}
