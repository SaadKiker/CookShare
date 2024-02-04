package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.InstructionStepDTO;
import com.project.cookshare.models.InstructionStep;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class InstructionStepMapper {

    // Method to convert a single DTO to an entity - corrected to use step_number
    public static InstructionStep mapToInstructionStepEntity(InstructionStepDTO dto) {
        InstructionStep entity = new InstructionStep();
        entity.setId(dto.getId());
        // Assuming setting the Recipe is handled elsewhere or not needed in this direct conversion
        entity.setStep_number(dto.getStep_number()); // Adjusted to use step_number
        entity.setInstruction(dto.getInstruction());
        return entity;
    }

    // Correctly handling conversion of a Set of DTOs to a Set of Entities
    public static Set<InstructionStep> mapToInstructionStepEntities(Set<InstructionStepDTO> dtos) {
        if (dtos == null) {
            return new HashSet<>();
        }
        return dtos.stream()
                .map(InstructionStepMapper::mapToInstructionStepEntity)
                .collect(Collectors.toSet());
    }

    // Method to convert back from an Entity to DTO, adjusted for step_number
    public static InstructionStepDTO mapToInstructionStepDTO(InstructionStep entity) {
        return InstructionStepDTO.builder()
                .id(entity.getId())
                // .recipe(RecipeMapper.mapToRecipeDTO(entity.getRecipe())) // Assuming a RecipeMapper exists
                .step_number(entity.getStep_number()) // Adjusted for step_number
                .instruction(entity.getInstruction())
                .build();
    }

    // Convert a Set of Entities to a Set of DTOs, also adjusted for step_number
    public static Set<InstructionStepDTO> mapToInstructionStepDTOs(Set<InstructionStep> entities) {
        if (entities == null) {
            return new HashSet<>();
        }
        return entities.stream()
                .map(InstructionStepMapper::mapToInstructionStepDTO)
                .collect(Collectors.toSet());
    }
}
