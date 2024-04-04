package com.example.projectforjob.services;

import com.example.projectforjob.models.Photo;
import com.example.projectforjob.models.Restaurant;
import com.example.projectforjob.repositories.PhotoRepositories;
import com.example.projectforjob.repositories.RestaurantsRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantsRepositories restaurantsRepositories;
    private final PhotoRepositories photoRepositories;

    @Autowired
    public RestaurantService(RestaurantsRepositories restaurantsRepositories, PhotoRepositories photoRepositories) {
        this.restaurantsRepositories = restaurantsRepositories;
        this.photoRepositories = photoRepositories;
    }

    public Restaurant findRestaurant(int id) {
        Optional<Restaurant> response = restaurantsRepositories.findById(id);

        return response.orElse(null);
    }

    public List<Restaurant> findAllRestaurants(int id) {
        List<Restaurant> restaurant = restaurantsRepositories.findByCity_Id(id);
        return restaurant;
    }

    public List<Photo> findPhotos(int id) {
        List<Photo> photos = photoRepositories.findByRestaurant_Id(id);
        return photos;
    }
}
