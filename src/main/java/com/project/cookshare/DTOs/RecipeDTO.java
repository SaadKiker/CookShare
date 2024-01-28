package com.project.cookshare.DTOs;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeDTO {
    private Integer id;
    private String title;
    private String description;
    private String instructions;
    private Date createdDate;
    private String cookingTime;
    private UserDTO author;
    private String image;
}
