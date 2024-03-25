package com.project.cookshare.services;

import com.project.cookshare.models.InstructionStep;
import com.project.cookshare.models.Recipe;

import java.util.List;

public interface InstructionStepService {

    List<InstructionStep> findByRecipeId(Integer Id);
    void deleteByRecipeId(Integer id);
}
