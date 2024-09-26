package com.example.projectforjob.repositories;

import com.example.projectforjob.models.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepositories extends JpaRepository<Attraction, Integer> {
}
