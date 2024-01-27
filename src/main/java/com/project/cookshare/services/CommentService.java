package com.project.cookshare.services;

import com.project.cookshare.DTOs.CommentDTO;
import com.project.cookshare.models.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {

    // Class Diagram Methods
    void addComment(Integer recipeId, CommentDTO commentDTO);
    void editComment(Integer commentId);
    void deleteCommentById(Integer commentId);

    // Additional Methods
    List<Comment> getAllComments();

}
