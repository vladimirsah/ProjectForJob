package com.example.projectforjob.controllers;

import com.example.projectforjob.models.City;
import com.example.projectforjob.models.Comment;
import com.example.projectforjob.models.Person;
import com.example.projectforjob.models.Restaurant;
import com.example.projectforjob.security.PersonDetail;
import com.example.projectforjob.services.CityService;
import com.example.projectforjob.services.CommentService;
import com.example.projectforjob.services.MenuService;
import com.example.projectforjob.services.PersonGetService;
import com.example.projectforjob.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RestaurantControllers {

    private final RestaurantService restaurantService;
    private final CityService cityService;
    private final CommentService commentService;
    private final MenuService menuService;
    private final PersonGetService personGetService;

    @Autowired
    public RestaurantControllers(RestaurantService restaurantService, CityService cityService, CommentService commentService, MenuService menuService, PersonGetService personGetService) {
        this.restaurantService = restaurantService;
        this.cityService = cityService;
        this.commentService = commentService;
        this.menuService = menuService;
        this.personGetService = personGetService;
    }

    @GetMapping("/city")
    public String getCity(@ModelAttribute("town") City city, Model model) {
        model.addAttribute("city", cityService.allCity());
        return "city/city";
    }

    @GetMapping("/restaurants")
    public String getAllRestaurants(@ModelAttribute("town") City city, Model model) {

        List<Restaurant> restaurants = restaurantService.findAllRestaurants(city.getId());
        restaurants.sort((r1, r2) -> Double.compare(r2.getAverageMark(), r1.getAverageMark()));
        model.addAttribute("restaurants", restaurants);
        return "restaurants/listOfRestaurants";
    }

    @GetMapping("/restaurants/{id}")
    public String getRestaurant(@PathVariable("id") int id, Model model,
                                @ModelAttribute("comment") Comment comment
    ) {

        model.addAttribute("restaurant", restaurantService.findRestaurant(id));
        model.addAttribute("comments", commentService.findAllComments(id));
        model.addAttribute("menu", menuService.getMenu(id));
        model.addAttribute("positions", menuService.getPositions(id));
        model.addAttribute("photos", restaurantService.findPhotos(id));

        List<Comment> com = commentService.findAllComments(id);

        double average_count = com.stream().mapToDouble(Comment::getMark).average().orElse(0.0);

        restaurantService.save(restaurantService.findRestaurant(id), average_count);
        model.addAttribute("count", average_count);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal != "anonymousUser") {
            Person person = ((PersonDetail) principal).getPerson();
            List<Integer> comments = com.stream().filter(comment1 -> comment1.getPerson().getId() == person.getId()).map(Comment::getCommentId).collect(Collectors.toList());
            model.addAttribute("comId", comments);
        }
        return "restaurants/restaurant";
    }

    @PostMapping("/restaurants/{id}")
    public String comment(
            @ModelAttribute("restaurant") Restaurant restaurant,
            @ModelAttribute("commenta") Comment comment,
            @AuthenticationPrincipal PersonDetail personDetail
    ) {
        commentService.saveComment(restaurant, personDetail.getPerson(), comment);
        return "redirect:{id}";
    }

    @DeleteMapping("/restaurants/{id}/comment/{commentId}")
    public String deleteComment(@PathVariable("commentId") Integer commentId,
                                @ModelAttribute("res") Restaurant restaurant) {

        commentService.delete(commentId);
        return "redirect:/restaurants/" + restaurant.getId();
    }


}
