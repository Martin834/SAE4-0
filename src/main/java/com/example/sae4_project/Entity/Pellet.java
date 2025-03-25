package com.example.sae4_project.Entity;

import com.example.sae4_project.Entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Pellet extends Entity {
    private Circle circle;

    public Pellet() {
        Random r = new Random();
        int xAleatoire = r.nextInt(801);
        int yAleatoire = r.nextInt(801);
        Circle circle = new Circle(xAleatoire, yAleatoire, 5);
        circle.setFill(Color.BLUE);
        this.circle = circle;
    }

    public Circle getCircle() {
        return this.circle;
    }

}
