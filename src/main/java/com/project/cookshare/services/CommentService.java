package com.project.cookshare.services;

import com.project.cookshare.models.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    Comment addComment(Comment comment);
    Optional<Comment> getCommentById(Integer id);
    List<Comment> getAllComments();
    Comment updateComment(Comment comment);
    void deleteCommentById(Integer id);
}
