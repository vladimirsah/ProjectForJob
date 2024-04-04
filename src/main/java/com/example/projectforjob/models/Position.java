package com.example.projectforjob.models;

import javax.persistence.*;

@Entity
@Table(name = "Position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private int position_id;

    @Column(name = "position")
    private String position;

    @ManyToOne
    @JoinColumn(name = "menu_id", referencedColumnName = "menu_id")
    private Menu menu;

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
