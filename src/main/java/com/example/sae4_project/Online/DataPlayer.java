package com.example.sae4_project.Online;

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
}
