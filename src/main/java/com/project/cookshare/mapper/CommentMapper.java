package com.project.cookshare.mapper;

import com.project.cookshare.DTOs.CommentDTO;
import com.project.cookshare.models.Comment;

public class CommentMapper {

    public static Comment mapToCommentEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setContent(commentDTO.getContent());
        comment.setPublishDate(commentDTO.getPublishDate());
        comment.setAuthor(UserMapper.mapToUserEntity(commentDTO.getAuthor()));
        comment.setRecipe(RecipeMapper.mapToRecipeEntity(commentDTO.getRecipe()));
        return comment;
    }

    public static CommentDTO mapToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .publishDate(comment.getPublishDate())
                .author(UserMapper.mapToUserDTO(comment.getAuthor()))
                .recipe(RecipeMapper.mapToRecipeDTO(comment.getRecipe()))
                .build();
    }
}
