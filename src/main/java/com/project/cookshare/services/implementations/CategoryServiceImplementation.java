package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.models.Category;
import com.project.cookshare.repositories.CategoryRepository;
import com.project.cookshare.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImplementation(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Class Diagram Methods
    @Override
    public void addRecipeToCategory(Integer recipeId, Category category) {
        // Implementation
    }

    @Override
    public void removeRecipeFromCategory(Integer recipeId, Category category) {
        // Implementation
    }

    @Override
    public CategoryDTO findCategoryByName(String CategoryName) {
        return null;
    }

    // Additional Methods
    @Override
    public List<Category> getAllCategories() {
        return null;
    }
}
