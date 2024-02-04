package com.project.cookshare.DTOs;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientDTO {
    private Integer id;
    private String name;
    private RecipeDTO recipe;
    private String quantity;
}
