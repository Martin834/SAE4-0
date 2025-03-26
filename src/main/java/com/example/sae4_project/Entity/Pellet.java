package com.example.sae4_project.Entity;

import com.example.sae4_project.Entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Pellet extends Entity {
    private int posX;
    private int posY;

    public Pellet() {
        super();

        Random r = new Random();
        this.posX = r.nextInt(801);
        this.posY = r.nextInt(801);
        this.setMass(1);
        circle = new Circle(this.posX, this.posY, 5);
        circle.setFill(Color.BLUE);

        this.identifier = this.posX * this.posY * r.nextInt(834);


    }

    public Pellet(double x, double y) {
        Circle circle = new Circle(x, y, 5);
        circle.setFill(Color.BLUE);
        this.circle = circle;
        this.setMass(1);

    }

    public Circle getCircle() {
        return circle;
    }

    public int getIdentifier() {
        return this.identifier;
    }

}
