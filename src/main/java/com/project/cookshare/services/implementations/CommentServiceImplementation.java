package com.project.cookshare.services.implementations;

import com.project.cookshare.DTOs.CommentDTO;
import com.project.cookshare.models.Comment;
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
    public void addComment(Integer recipeId, CommentDTO commentDTO) {
            // This method would typically be in a CommentService, but if it's here:
            // TODO: Convert the commentDTO to a Comment entity and save it
            }

    @Override
    public void deleteCommentById(Integer commentId) {
            // This method would typically be in a CommentService, but if it's here:
            // TODO: Delete the comment by id
            }

    @Override
    public List<Comment> getAllComments() {
        return null;
    }

    @Override
    public void editComment(Integer commentId) {
    }

}
