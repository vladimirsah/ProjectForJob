package com.example.projectforjob.controllers;

import com.example.projectforjob.services.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AttractionControllers {

    private final AttractionService attractionService;

    @Autowired
    public AttractionControllers(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping("/attractions")
    public String attractions(Model model) {

        model.addAttribute("attractions", attractionService.getAttractions());
        return "attractions/attractions";
    }

    @GetMapping("/attraction/{id}")
    public String atrraction(@PathVariable("id") int id, Model model) {
        model.addAttribute("attraction", attractionService.getAttraction(id));
        return "attractions/attraction";

    }
}
