package com.example.projectforjob.controllers;

import com.example.projectforjob.models.Person;
import com.example.projectforjob.repositories.PeopleRepositories;
import com.example.projectforjob.security.PersonDetail;
import com.example.projectforjob.services.PersonDetailService;
import com.example.projectforjob.services.PersonGetService;
import com.example.projectforjob.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
public class UserControllers {

    private final PersonDetailService personDetailService;
    private final PersonGetService personGetService;
    private final PeopleRepositories peopleRepositories;
    private final PictureService pictureService;

    @Autowired
    public UserControllers(PersonDetailService personDetailService, PersonGetService personGetService, PeopleRepositories peopleRepositories, PictureService pictureService) {
        this.personDetailService = personDetailService;
        this.personGetService = personGetService;
        this.peopleRepositories = peopleRepositories;
        this.pictureService = pictureService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "base/hi";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "base/admin";
    }

    @GetMapping("/basis")
    public String base() {
        return "base/basis";
    }

    @GetMapping("/user")
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    public String userPage(Principal principal, Model model) {
        model.addAttribute("person", peopleRepositories.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("unable to fing user by username: " + principal.getName())));
        return "base/userPage";
    }

    @GetMapping("/user/edit")
    public String userEdit(Authentication authentication, Model model) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        PersonDetail person = (PersonDetail) userDetails;


        model.addAttribute("person", person.getPerson());

        return "base/userPageEdit";
    }

    @PostMapping("/user/edit")
    public String userEdit(@ModelAttribute("person") Person person, @RequestParam("file") MultipartFile multipartFile) throws IOException {

        personGetService.update(person);
        pictureService.savePicture(person,
                pictureService.findById(person.getId()),
                multipartFile
        );

        return "redirect:/user";

    }


}



