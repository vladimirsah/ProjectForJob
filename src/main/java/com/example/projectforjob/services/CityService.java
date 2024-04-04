package com.example.projectforjob.services;

import com.example.projectforjob.models.City;
import com.example.projectforjob.repositories.CityRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepositories cityRepositories;

    @Autowired
    public CityService(CityRepositories cityRepositories) {
        this.cityRepositories = cityRepositories;
    }

    public List<City> allCity() {

        return cityRepositories.findAll();
    }
}
