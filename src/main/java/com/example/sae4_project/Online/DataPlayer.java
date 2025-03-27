package com.example.sae4_project.Online;

import com.example.sae4_project.Entity.Player;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class DataPlayer {
    private int id;

    public int getId() {
        return id;
    }

    public double getMass() {
        return mass;
    }


    private double mass;

    public ArrayList<DataCircle> getDataCircles() {
        return dataCircles;
    }

    private ArrayList<DataCircle> dataCircles;

    DataPlayer(int id, double mass) {
        this.id = id;
        this.mass = mass;
        this.dataCircles = new ArrayList<>();
    }

    public DataPlayer(Player player){
        this.id = player.getIdentifier();
        this.mass = player.getMass();
        this.dataCircles = new ArrayList<>();
        for(Circle circle : player.getCirclesList()){
            this.getDataCircles().add(
                    new DataCircle(circle.getCenterX(), circle.getCenterY(), circle.getRadius())
            );
        }
    }
}
