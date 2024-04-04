package com.example.projectforjob.repositories;

import com.example.projectforjob.models.Position;
import com.example.projectforjob.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepositories extends JpaRepository<Position, Integer> {
    @Query(nativeQuery = true, value = "select position, position_id, M.menu_id from Restaurant join Menu M on Restaurant.Restaurant_id = M.restaurant_id join Position P on M.menu_id = P.menu_id WHERE M.restaurant_id = :id")
    List<Position> findPositionByRestaurant_Id(@Param("id") int id);

}
