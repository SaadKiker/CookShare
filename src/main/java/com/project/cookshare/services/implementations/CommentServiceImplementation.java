package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.CommentDTO;
import com.project.cookshare.mapper.CommentMapper;
import com.project.cookshare.models.Comment;
import com.project.cookshare.models.Recipe;
import com.project.cookshare.repositories.CommentRepository;
import com.project.cookshare.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImplementation implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImplementation(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public void addComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void editComment(Integer commentId) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));
        commentRepository.save(existingComment);
    }

    @Override
    public void deleteCommentById(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @Override
    public long countComments() {
        return commentRepository.count(); // Assuming you have a JPA repository
    }

    @Override
    public int countCommentsByRecipeId(Integer id) {
        List<Comment> comments = commentRepository.findByRecipeId(id);
        return comments.size();
    }

}
