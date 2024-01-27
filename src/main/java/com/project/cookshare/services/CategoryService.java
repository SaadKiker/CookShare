package com.project.cookshare.services;

import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.models.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    // Class Diagram Methods
    void addRecipeToCategory(Integer recipeId, Category category);
    void removeRecipeFromCategory(Integer recipeId, Category category);

    // Additional Methods
    CategoryDTO findCategoryByName(String CategoryName);
    List<Category> getAllCategories();
}
