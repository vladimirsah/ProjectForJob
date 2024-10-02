package com.example.projectforjob.services;

import com.example.projectforjob.models.Comment;
import com.example.projectforjob.models.Photo;
import com.example.projectforjob.models.Restaurant;
import com.example.projectforjob.repositories.PhotoRepositories;
import com.example.projectforjob.repositories.RestaurantsRepositories;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
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

    public void save(Restaurant restaurant, double mark) {
        restaurant.setAverageMark(mark);
        restaurantsRepositories.save(restaurant);
    }

    public String getCoordinates(int id) throws IOException, InterruptedException {
        String urlGeocoderApi = "https://catalog.api.2gis.com/3.0/items/geocode?q=";
        String paramGeocoderApi = "&fields=items.point&key=493a6277-1562-44c5-a05d-48c7d18c1576";
        String address = URLEncoder.encode(restaurantsRepositories.findById(id).get().getAddress(), "UTF-8");

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest
            .newBuilder()
            .uri(URI.create(urlGeocoderApi + address + paramGeocoderApi))
            .GET()
            .build();

        HttpResponse<String> response = httpClient
            .send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper1 = new ObjectMapper();
        JsonNode jsonNode1 = objectMapper1.readTree(response.body());
        JsonNode js = jsonNode1.path("result").path("items").get(0).path("point");

        double lat = js.path("lat").asDouble();
        double lon = js.path("lon").asDouble();

        return lon + "," + lat;
    }

}
