package com.project.cookshare.repositories;

import com.project.cookshare.DTOs.RecipeDTO;
import com.project.cookshare.DTOs.UserDTO;
import com.project.cookshare.models.InstructionStep;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    Recipe findByTitle(String title);
    List<Recipe> findByCategoryName(String categoryName);
    List<Recipe> findByAuthor(User author);
    int countByAuthorId(int authorId);
}
