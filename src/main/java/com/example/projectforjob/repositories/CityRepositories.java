package com.example.projectforjob.repositories;

import com.example.projectforjob.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepositories extends JpaRepository<City, Integer> {
}
