package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.InstructionStepDTO;
import com.project.cookshare.models.InstructionStep;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class InstructionStepMapper {

    public static InstructionStep mapToInstructionStepEntity(InstructionStepDTO dto) {
        InstructionStep entity = new InstructionStep();
        entity.setId(dto.getId());
        entity.setStep_number(dto.getStep_number());
        entity.setInstruction(dto.getInstruction());
        return entity;
    }

    public static Set<InstructionStep> mapToInstructionStepEntities(Set<InstructionStepDTO> dtos) {
        if (dtos == null) {
            return new HashSet<>();
        }
        return dtos.stream()
                .map(InstructionStepMapper::mapToInstructionStepEntity)
                .collect(Collectors.toSet());
    }

    public static InstructionStepDTO mapToInstructionStepDTO(InstructionStep entity) {
        return InstructionStepDTO.builder()
                .id(entity.getId())
                .step_number(entity.getStep_number())
                .instruction(entity.getInstruction())
                .build();
    }

    public static Set<InstructionStepDTO> mapToInstructionStepDTOs(Set<InstructionStep> entities) {
        if (entities == null) {
            return new HashSet<>();
        }
        return entities.stream()
                .map(InstructionStepMapper::mapToInstructionStepDTO)
                .collect(Collectors.toSet());
    }
}
