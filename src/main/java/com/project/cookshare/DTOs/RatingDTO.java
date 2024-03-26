package com.project.cookshare.DTOs;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingDTO {

    private Integer id;
    private Integer score;
    private Integer averageScore;
    private RecipeDTO recipe;
}
