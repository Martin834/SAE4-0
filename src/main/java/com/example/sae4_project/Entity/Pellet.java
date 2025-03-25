package com.example.sae4_project.Entity;

import com.example.sae4_project.Entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Pellet extends Entity {
    private Circle circle;

    public Pellet() {
        Random random = new Random();
        int xAleatoire = random.nextInt(801);
        int yAleatoire = random.nextInt(801);
        Circle circle = new Circle(xAleatoire, yAleatoire, 5);
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        circle.setFill(Color.rgb(r,g,b));
        this.circle = circle;
    }

    public Circle getCircle() {
        return this.circle;
    }

}
