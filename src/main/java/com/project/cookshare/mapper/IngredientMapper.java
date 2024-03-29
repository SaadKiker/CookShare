package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.IngredientDTO;
import com.project.cookshare.models.Ingredient;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class IngredientMapper {

    public static Ingredient mapToIngredientEntity(IngredientDTO dto) {
        Ingredient entity = new Ingredient();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setQuantity(dto.getQuantity());
        return entity;
    }

    public static Set<Ingredient> mapToIngredientEntities(Set<IngredientDTO> dtos) {
        if (dtos == null) {
            return new HashSet<>();
        }
        return dtos.stream()
                .map(IngredientMapper::mapToIngredientEntity)
                .collect(Collectors.toSet());
    }

    public static IngredientDTO mapToIngredientDTO(Ingredient entity) {
        return IngredientDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .quantity(entity.getQuantity())
                .build();
    }

    public static Set<IngredientDTO> mapToIngredientDTOs(Set<Ingredient> entities) {
        if (entities == null) {
            return new HashSet<>();
        }
        return entities.stream()
                .map(IngredientMapper::mapToIngredientDTO)
                .collect(Collectors.toSet());
    }
}
