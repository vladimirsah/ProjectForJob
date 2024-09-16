package com.example.projectforjob.services;

import com.example.projectforjob.models.Comment;
import com.example.projectforjob.models.Person;
import com.example.projectforjob.models.Restaurant;
import com.example.projectforjob.repositories.CommentRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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
    public void saveComment(Restaurant restaurant, Person person, Comment comment) {
        comment.setRestaurant(restaurant);
        comment.setPerson(person);
        commentRepositories.save(comment);
    }

    public void delete(int id) {
        commentRepositories.deleteById(id);
    }

//    public Restaurant findRest(Comment comment){
//        Restaurant restaurant =
//    }
}
