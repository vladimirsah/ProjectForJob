package com.example.projectforjob.repositories;

import com.example.projectforjob.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantsRepositories extends JpaRepository<Restaurant, Integer> {
    List<Restaurant> findByCity_Id(int id);
}
