package com.project.cookshare.repositories;

import com.project.cookshare.models.Comment;
import com.project.cookshare.models.Ingredient;
import com.project.cookshare.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByRecipe(Recipe recipe);
    List<Comment> findByRecipeId(Integer recipeId);

}
