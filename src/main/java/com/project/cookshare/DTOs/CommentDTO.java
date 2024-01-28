package com.project.cookshare.DTOs;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    private Integer id;
    private String content;
    private Date publishDate;
    private UserDTO author;
    private RecipeDTO recipe;
}
