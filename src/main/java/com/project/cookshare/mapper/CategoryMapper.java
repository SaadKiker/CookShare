package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.models.Category;

public class CategoryMapper {

    public static Category mapToCategoryEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }

    public static CategoryDTO mapToCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
