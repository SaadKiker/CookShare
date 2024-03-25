package com.project.cookshare.services.implementations;

import com.project.cookshare.models.InstructionStep;
import com.project.cookshare.repositories.InstructionStepRepository;
import com.project.cookshare.services.InstructionStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructionStepServiceImplementation implements InstructionStepService {

    private final InstructionStepRepository instructionStepRepository;

    @Autowired
    public InstructionStepServiceImplementation(InstructionStepRepository instructionStepRepository) {
        this.instructionStepRepository = instructionStepRepository;
    }

    @Override
    public List<InstructionStep> findByRecipeId(Integer id) {
        return instructionStepRepository.findByRecipeId(id);
    }

    @Override
    public void deleteByRecipeId(Integer id) {
        instructionStepRepository.deleteAllByRecipeId(id);
    }
}
