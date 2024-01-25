package com.project.cookshare.services.implementations;

import com.project.cookshare.models.Comment;
import com.project.cookshare.repositories.CommentRepository;
import com.project.cookshare.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

//@Service
//public class CommentServiceImplementation implements CommentService {
//
//    private final CommentRepository commentRepository;
//
//    @Autowired
//    public CommentService(CommentRepository commentRepository) {
//        this.commentRepository = commentRepository;
//    }
//
//    @Override
//    public Comment addComment(Comment comment) {
//        // Implementation
//    }
//
//    @Override
//    public Optional<Comment> getCommentById(Integer id) {
//        // Implementation
//    }
//
//    @Override
//    public List<Comment> getAllComments() {
//        // Implementation
//    }
//
//    @Override
//    public Comment updateComment(Comment comment) {
//        // Implementation
//    }
//
//    @Override
//    public void deleteCommentById(Integer id) {
//        // Implementation
//    }
//}
