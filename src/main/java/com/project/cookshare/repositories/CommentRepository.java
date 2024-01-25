package com.project.cookshare.repositories;

import com.project.cookshare.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // Additional query methods can be defined here
}
