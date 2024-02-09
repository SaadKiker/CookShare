package com.project.cookshare.repositories;

import com.project.cookshare.models.InstructionStep;
import com.project.cookshare.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Recipe findByTitle(String title);
    List<Recipe> findByCategoryName(String categoryName);
    int countByAuthorId(int authorId);

}
