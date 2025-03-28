package com.example.sae4_project.Entity;

import com.example.sae4_project.Entity.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Pellet extends Entity {
    private int posX;
    private int posY;

    /**
     * Pellet object constructor. Gives it a random color and sets it mass to 5, so that players grow faster when
     * eating it.
     * @param x
     * @param y
     */
    public Pellet(double x, double y) {
        super();
        this.setMass(5);
        circle = new Circle(x, y, 2);
        Random rand = new Random();
        double red = rand.nextDouble();
        double green = rand.nextDouble();
        double blue = rand.nextDouble();

        circle.setFill(new Color(red, green, blue, 1));
    }

    /**
     * @return the radius of this pellet
     */
    public double calculateRadius() {
        double mass = this.mass.get();
        return 10 * Math.sqrt(mass);
    }

    /**
     *
     * @return the circle object this Pellet object holds
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     *
     * @return this Pellet object's identifier.
     */
    public int getIdentifier() {
        return this.identifier;
    }

}