package com.example.projectforjob.repositories;

import com.example.projectforjob.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepositories extends JpaRepository<Photo, Integer> {

    List<Photo> findByRestaurant_Id(int id);
}
