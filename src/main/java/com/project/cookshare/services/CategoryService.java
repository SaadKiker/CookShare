package com.project.cookshare.services;

import com.project.cookshare.models.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Category addCategory(Category category);
    Optional<Category> getCategoryById(Integer id);
    List<Category> getAllCategories();
    Category updateCategory(Category category);
    void deleteCategoryById(Integer id);
}
