package com.example.sae4_project.Entity;

import com.example.sae4_project.Entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Pellet extends Entity  {
    private int posX;
    private int posY;
    public Pellet(double x, double y) {
        super();
        this.setMass(2);
        circle = new Circle(x, y, 2);
        Random rand = new Random();
        double red = rand.nextDouble();
        double green = rand.nextDouble();
        double blue = rand.nextDouble();

        circle.setFill(new Color(red, green, blue, 1));
    }
    public double calculateRadius() {
        double mass = this.mass.get();
        return 10 * Math.sqrt(mass);
    }

    public Circle getCircle() {
        return circle;
    }



}