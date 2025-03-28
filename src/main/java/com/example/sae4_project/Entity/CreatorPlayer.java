package com.example.sae4_project.Entity;


import com.example.sae4_project.Online.DataCircle;
import com.example.sae4_project.Online.DataPlayer;
import javafx.scene.shape.Circle;

import java.util.List;

public class CreatorPlayer extends Creator {

    /**
     * Calls the create method once a CreatorPlayer is made
     */
    public CreatorPlayer() {
        this.create();
    }

    /**
     * Creates a new Player by calling the Player constructor
     * @return
     */
    @Override
    public Player create() {
        return new Player();
    }

    /**
     * Creates an empty Pellet object
     * @param x
     * @param y
     * @return
     */
    @Override
    public Entity create(double x, double y) {
        return null;
    }

    public Player createFromData(DataPlayer data){
        Player player = new Player();
        player.setMass(data.getMass());
        player.setIdentifier(data.getId());

        player.getCirclesList().clear();
        int i = 0;
        for(DataCircle dataCircle : data.getDataCircles()){
            if(i > 0){
                player.divideItself();
            }
            player.getCirclesList().get(i).setCenterX(dataCircle.getCenterX());
            player.getCirclesList().get(i).setCenterY(dataCircle.getCenterX());
            player.getCirclesList().get(i).setRadius(dataCircle.getRadius());
        }

        return player;
    }
}
