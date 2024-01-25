package com.project.cookshare.services.implementations;

import com.project.cookshare.models.Category;
import com.project.cookshare.repositories.CategoryRepository;
import com.project.cookshare.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//@Service
//public class CategoryServiceImplementation implements CategoryService {
//
//    private final CategoryRepository categoryRepository;
//
//    @Autowired
//    public CategoryService(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }
//
//    @Override
//    public Category addCategory(Category category) {
//        // Implementation
//    }
//
//    @Override
//    public Optional<Category> getCategoryById(Integer id) {
//        // Implementation
//    }
//
//    @Override
//    public List<Category> getAllCategories() {
//        // Implementation
//    }
//
//    @Override
//    public Category updateCategory(Category category) {
//        // Implementation
//    }
//
//    @Override
//    public void deleteCategoryById(Integer id) {
//        // Implementation
//    }
//}
