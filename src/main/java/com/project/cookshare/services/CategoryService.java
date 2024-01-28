package com.project.cookshare.services;

import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.models.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {

    // Class Diagram Methods
    void addRecipeToCategory(Integer recipeId, Integer category_id);
    void removeRecipeFromCategory(Integer recipeId, Integer category_id);
    // Additional Methods
    CategoryDTO findCategoryByName(String CategoryName);
    List<Category> getAllCategories();
}
