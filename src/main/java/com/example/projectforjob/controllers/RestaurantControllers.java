package com.example.projectforjob.controllers;

import com.example.projectforjob.models.City;
import com.example.projectforjob.models.Comment;
import com.example.projectforjob.models.Person;
import com.example.projectforjob.models.Restaurant;
import com.example.projectforjob.security.PersonDetail;
import com.example.projectforjob.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        model.addAttribute("restaurants", restaurantService.findAllRestaurants(city.getId()));
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
        double count = 0;
        int k = 0;
        for (Comment mark : com) {
            count += mark.getMark();
            k++;
        }
        count = count / k;
        model.addAttribute("count", count);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal != "anonymousUser") {
            Person person = ((PersonDetail) principal).getPerson();

            List<Integer> listId = new ArrayList<>();
            int c = 0;
            for (Comment commentary : com) {
                if (commentary.getPerson().getId() == person.getId()) {
                    listId.add(c, commentary.getCommentId());
                    c++;
                }
            }
            model.addAttribute("comId", listId);
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
