package com.project.cookshare.DTOs;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private Integer id;
    private String name;
    private String description;
}
