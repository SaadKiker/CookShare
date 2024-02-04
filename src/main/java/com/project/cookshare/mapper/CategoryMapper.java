package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.models.Category;
import java.util.Set;
import java.util.stream.Collectors;

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

    public static Set<Category> mapToCategoryEntities(Set<CategoryDTO> categoryDTOs) {
        return categoryDTOs.stream()
                .map(CategoryMapper::mapToCategoryEntity)
                .collect(Collectors.toSet());
    }

    public static Set<CategoryDTO> mapToCategoryDTOs(Set<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::mapToCategoryDTO)
                .collect(Collectors.toSet());
    }
}
