package com.project.cookshare.DTOs;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeDTO {
    private Integer id;
    private String title;
    private String description;
    private CategoryDTO category;
    private Date created_date;
    private String cooking_time;
    private UserDTO author;
    private String image;
}
