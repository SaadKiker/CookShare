package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.FavoriteDTO;
import com.project.cookshare.models.Favorite;

public class FavoriteMapper {

    public static Favorite mapToFavoriteEntity(FavoriteDTO favoriteDTO) {
        Favorite favorite = new Favorite();
        favorite.setId(favoriteDTO.getId());
        favorite.setUser(UserMapper.mapToUserEntity(favoriteDTO.getUser()));
        favorite.setRecipe(RecipeMapper.mapToRecipeEntity(favoriteDTO.getRecipe()));
        return favorite;
    }

    public static FavoriteDTO mapToFavoriteDTO(Favorite favorite) {
        return FavoriteDTO.builder()
                .id(favorite.getId())
                .user(UserMapper.mapToUserDTO(favorite.getUser()))
                .recipe(RecipeMapper.mapToRecipeDTO(favorite.getRecipe()))
                .build();
    }
}
