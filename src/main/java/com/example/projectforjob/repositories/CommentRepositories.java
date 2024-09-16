package com.example.projectforjob.repositories;

import com.example.projectforjob.models.Comment;
import com.example.projectforjob.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepositories extends JpaRepository<Comment, Integer> {
    List<Comment> findByRestaurant_Id(int id);

}
