package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.CategoryDTO;
import com.project.cookshare.mapper.CategoryMapper;
import com.project.cookshare.models.Category;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.repositories.CategoryRepository;
import com.project.cookshare.repositories.RecipeRepository;
import com.project.cookshare.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public CategoryServiceImplementation(CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }


    @Override
    public CategoryDTO findCategoryByName(String categoryName) {
        Category category = categoryRepository.findByName(categoryName)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        return CategoryMapper.mapToCategoryDTO(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
