package com.example.projectforjob.services;

import com.example.projectforjob.models.Attraction;
import com.example.projectforjob.repositories.AttractionRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionService {

    private final AttractionRepositories attractionRepositories;

    @Autowired
    public AttractionService(AttractionRepositories attractionRepositories) {
        this.attractionRepositories = attractionRepositories;
    }

    public List<Attraction> getAttractions() {
        return attractionRepositories.findAll();
    }

    public Attraction getAttraction(int id) {
        return attractionRepositories.getById(id);
    }
}
