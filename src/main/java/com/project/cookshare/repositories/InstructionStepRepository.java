package com.project.cookshare.repositories;

import com.project.cookshare.models.InstructionStep;
import com.project.cookshare.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructionStepRepository extends JpaRepository<InstructionStep, Integer> {
    // If you need to find InstructionSteps by some property, you can add methods here.
    // For example, to find by recipe, assuming InstructionStep has a reference to Recipe:
    List<InstructionStep> findByRecipe(Recipe recipe);

    // If you just need to find all, you can use findAll inherited from JpaRepository
}
