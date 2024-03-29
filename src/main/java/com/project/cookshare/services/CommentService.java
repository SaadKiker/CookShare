package com.project.cookshare.services;

import com.project.cookshare.DTOs.CommentDTO;
import com.project.cookshare.models.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {

    void addComment(Comment comment);
    int countCommentsByRecipeId(Integer id);

}
