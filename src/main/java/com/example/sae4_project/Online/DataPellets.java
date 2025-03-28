package com.example.sae4_project.Online;

import java.io.Serializable;

public class DataPellets implements Serializable {

    private int id;

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMass() {
        return mass;
    }


    private double x;
    private double y;
    private double mass;

    public DataPellets(int id, double x, double y, double mass) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.mass = mass;
    }
}
