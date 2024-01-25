package com.project.cookshare.repositories;

import com.project.cookshare.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Additional query methods can be defined here
}
