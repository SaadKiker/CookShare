package com.project.cookshare.repositories;

import com.project.cookshare.models.InstructionStep;
import com.project.cookshare.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructionStepRepository extends JpaRepository<InstructionStep, Integer> {
    List<InstructionStep> findByRecipeId(Integer id);
    List<InstructionStep> findByRecipe(Recipe recipe);
    void deleteAllByRecipeId(Integer id);
}
