package com.example.sae4_project.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Map;

public class Player extends Entity {

    private double speed;
    private double radius;
    private Circle circle;
    private double smoothing = 80;

    public Player() {
        this.radius=25;

        Circle circle = new Circle(400-this.radius/2, 300-this.radius/2, this.radius);
        circle.setFill(Color.RED);
        this.circle = circle;
    }

    public void moveTowards(double posXMouse, double posYMouse) {

        double[] velocity = new double[]{posXMouse - this.circle.getCenterX(), posYMouse - this.circle.getCenterY()};
        double euclidianDistance = Math.sqrt((velocity[0] * velocity[0]) + (velocity[1] * velocity[1])) / this.smoothing;

        velocity = normalizeDouble(velocity);

        this.circle.setCenterX(this.circle.getCenterX() + velocity[0]);
        this.circle.setCenterY(this.circle.getCenterY() + velocity[1]);
    }

    public double[] normalizeDouble(double[] array){

        double magnitude = Math.sqrt( (array[0] * array[0]) + (array[1] * array[1]) );

        if (array[0] != 0 || array[1] != 0 ){
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0,0};
    }


    public Circle getCircle() {
        return this.circle;
    }

    public double calculateRadius() {
        //TODO A IMPLEMENTER
        return 0.0;
    }

    public double calculateSpeed() {
        //TODO A IMPLEMENTER
        return 0.0;
    }

    public double calculateMaxSpeed() {
        //TODO A IMPLEMENTER
        return 0.0;
    }

    public boolean canEat(Player player) {
        //TODO A IMPLEMENTER
        return false;
    }

    public void eat(Map map) {
        //TODO A IMPLEMENTER
    }

    public double calculateEventHorizon() {
        //TODO A IMPLEMENTER
        return 0.0;
    }
}
