package com.example.projectforjob.controllers;

import com.example.projectforjob.models.City;
import com.example.projectforjob.models.Comment;
import com.example.projectforjob.models.Restaurant;
import com.example.projectforjob.services.CityService;
import com.example.projectforjob.services.CommentService;
import com.example.projectforjob.services.MenuService;
import com.example.projectforjob.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RestaurantControllers {

    private final RestaurantService restaurantService;
    private final CityService cityService;
    private final CommentService commentService;
    private final MenuService menuService;

    @Autowired
    public RestaurantControllers(RestaurantService restaurantService, CityService cityService, CommentService commentService, MenuService menuService) {
        this.restaurantService = restaurantService;
        this.cityService = cityService;
        this.commentService = commentService;
        this.menuService = menuService;
    }

    @GetMapping("/city")
    public String getCity(@ModelAttribute("town") City city, Model model) {
        model.addAttribute("city", cityService.allCity());
        return "city/city";
    }

    @GetMapping("/restaurants")
    public String getAllRestaurants(@ModelAttribute("town") City city, Model model) {

        model.addAttribute("restaurants", restaurantService.findAllRestaurants(city.getId()));
        return "restaurants/listOfRestaurants";
    }

    @GetMapping("/restaurants/{id}")
    public String getRestaurant(@PathVariable("id") int id, Model model, @ModelAttribute("comment") Comment comment) {

        model.addAttribute("restaurant", restaurantService.findRestaurant(id));
        model.addAttribute("comments", commentService.findAllComments(id));
        model.addAttribute("menu", menuService.getMenu(id));
        model.addAttribute("positions", menuService.getPositions(id));
        model.addAttribute("photos", restaurantService.findPhotos(id));
        return "restaurants/restaurant";
    }

    @PostMapping("/restaurants/{id}")
    public String comment(@ModelAttribute("restaurant") Restaurant restaurant, @ModelAttribute("commenta") Comment comment) {

        commentService.saveComment(restaurant, comment);
        return "redirect:{id}";
    }


}
