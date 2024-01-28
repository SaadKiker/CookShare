package com.project.cookshare.DTOs;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FavoriteDTO {
    private Integer id;
    private UserDTO user;
    private RecipeDTO recipe;
}
