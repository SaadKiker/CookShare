package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.CommentDTO;
import com.project.cookshare.models.Comment;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CommentMapper {

    public static Comment mapToCommentEntity(CommentDTO dto) {
        Comment entity = new Comment();
        entity.setId(dto.getId());
        entity.setContent(dto.getContent());
        entity.setPublishDate(dto.getPublishDate());
        entity.setAuthor(UserMapper.mapToUserEntity(dto.getAuthor()));
        entity.setRecipe(RecipeMapper.mapToRecipeEntity(dto.getRecipe()));
        return entity;
    }

    public static Set<Comment> mapToCommentEntities(Set<CommentDTO> dtos) {
        if (dtos == null) {
            return new HashSet<>();
        }
        return dtos.stream()
                .map(CommentMapper::mapToCommentEntity)
                .collect(Collectors.toSet());
    }

    public static CommentDTO mapToCommentDTO(Comment entity) {
        return CommentDTO.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .publishDate(entity.getPublishDate())
                .author(UserMapper.mapToUserDTO(entity.getAuthor()))
                .recipe(RecipeMapper.mapToRecipeDTO(entity.getRecipe()))
                .build();
    }

    public static Set<CommentDTO> mapToCommentDTOs(Set<Comment> entities) {
        if (entities == null) {
            return new HashSet<>();
        }
        return entities.stream()
                .map(CommentMapper::mapToCommentDTO)
                .collect(Collectors.toSet());
    }
}
