package com.example.sae4_project.Online;

import java.io.Serializable;

public class DataCircle implements Serializable {

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }

    private double centerX;
    private double centerY;
    private double radius;
    public DataCircle(double centerX, double centerY, double radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }
}
