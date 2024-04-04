package com.example.projectforjob.repositories;

import com.example.projectforjob.models.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepositories extends JpaRepository<Menu, Integer> {
    List<Menu> findAllByRestaurant_Id(int id);

}
