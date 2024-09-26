package com.example.projectforjob.controllers;

import com.example.projectforjob.models.Person;
import com.example.projectforjob.services.PersonDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final PersonDetailService personDetailService;

    public AuthController(PersonDetailService personDetailService) {
        this.personDetailService = personDetailService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person) {

        return "auth/registration";

    }

    @PostMapping("/registration")
    public String reg(@ModelAttribute("person") Person person) {

        personDetailService.save(person);

        return "redirect:/auth/login";
    }

    @GetMapping("/auth")
    public String button() {
        return "auth/button";
    }
}
