package com.example.sae4_project.Entity;


import com.example.sae4_project.Online.DataCircle;
import com.example.sae4_project.Online.DataPlayer;
import javafx.scene.shape.Circle;

import java.util.List;

public class CreatorPlayer extends Creator {

    public CreatorPlayer() {
        this.create();
    }

    @Override
    public Player create() {
        return new Player();
    }

    @Override
    public Entity create(double x, double y) {
        return null;
    }

    public Player createFromData(DataPlayer data){
        Player player = new Player();
        player.setMass(data.getMass());
        player.setIdentifier(data.getId());

        player.getCirclesList().clear();
        for(DataCircle dataCircle : data.getDataCircles()){
            player.getCirclesList().add(new Circle(
               dataCircle.getCenterX(),
               dataCircle.getCenterY(),
               dataCircle.getRadius()
            ));
        }

        return player;
    }
}
