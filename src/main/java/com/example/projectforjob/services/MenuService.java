package com.example.projectforjob.services;

import com.example.projectforjob.models.Menu;
import com.example.projectforjob.models.Position;
import com.example.projectforjob.repositories.MenuRepositories;
import com.example.projectforjob.repositories.PositionRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepositories menuRepositories;
    private final PositionRepositories positionRepositories;

    @Autowired
    public MenuService(MenuRepositories menuRepositories, PositionRepositories positionRepositories) {
        this.menuRepositories = menuRepositories;
        this.positionRepositories = positionRepositories;
    }

    public List<Menu> getMenu(int id) {
        List<Menu> list = menuRepositories.findAllByRestaurant_Id(id);
        return list;
    }

    public List<Position> getPositions(int id) {
        List<Position> list = positionRepositories.findPositionByRestaurant_Id(id);
        return list;
    }

}
