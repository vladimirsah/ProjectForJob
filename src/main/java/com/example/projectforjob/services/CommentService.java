package com.example.projectforjob.services;

import com.example.projectforjob.models.Comment;
import com.example.projectforjob.models.Restaurant;
import com.example.projectforjob.repositories.CommentRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepositories commentRepositories;

    @Autowired
    public CommentService(CommentRepositories commentRepositories) {
        this.commentRepositories = commentRepositories;
    }

    public List<Comment> findAllComments(int id) {
        List<Comment> list = commentRepositories.findByRestaurant_Id(id);
        return list;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public void saveComment(Restaurant restaurant, Comment comment) {
        comment.setRestaurant(restaurant);

        commentRepositories.save(comment);
    }
}
