package com.project.cookshare.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstructionStepDTO {
    private Integer id;
    private RecipeDTO recipe;
    private int step_number;
    private String instruction;

}
