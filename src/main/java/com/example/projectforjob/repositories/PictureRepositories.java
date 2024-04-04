package com.example.projectforjob.repositories;

import com.example.projectforjob.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PictureRepositories extends JpaRepository<Picture, Integer> {

    Optional<Picture> findByPerson_Id(int id);

}

