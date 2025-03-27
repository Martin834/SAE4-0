package com.example.sae4_project.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class SpecialPellets extends Entity{
    private int posX;
    private int posY;

    public SpecialPellets() {
        super();
        Random r = new Random();
        this.posX = r.nextInt(801);
        this.posY = r.nextInt(801);
        this.setMass(2);
        circle = new Circle(this.posX, this.posY, calculateRadius());
        circle.setFill(Color.DARKGREEN);
        this.identifier = this.posX * this.posY * r.nextInt(834);


    }

    public SpecialPellets(double x, double y) {
        super();
        this.setMass(2);
        circle = new Circle(x, y, calculateRadius());
        circle.setFill(Color.DARKGREEN);
    }
    public double calculateRadius() {
        double mass = this.mass.get();
        return 10 * Math.sqrt(mass);
    }

    public Circle getCircle() {
        return circle;
    }
}
