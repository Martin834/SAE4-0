package com.example.sae4_project.Entity;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public abstract class MoveableBody extends Entity {

    public double smoothing = 80;

    public double speed = 1;

    public double[] velocity = new double[2];
    public ArrayList<Circle> circlesList = new ArrayList<>();

    public void moveTowards(double posXMouse, double posYMouse, double maxSpeed) {

        velocity = new double[]{posXMouse - this.circle.getCenterX(), posYMouse - this.circle.getCenterY()};

        double euclidianDistance = Math.sqrt((velocity[0] * velocity[0]) + (velocity[1] * velocity[1]));

        double adjustedSpeed = Math.min(euclidianDistance / 100, maxSpeed);

        if (euclidianDistance > 4) {
            euclidianDistance = 4 * speed;
        }

        velocity = normalizeDouble(velocity);

        velocity[0] *= adjustedSpeed;
        velocity[1] *= adjustedSpeed;
    }

    public void move() {
        for (Circle circle : this.circlesList) {
            circle.setCenterX(circle.getCenterX() + velocity[0]);
            circle.setCenterY(circle.getCenterY() + velocity[1]);
        }

    }

    public double[] normalizeDouble(double[] array){

        double magnitude = Math.sqrt( (array[0] * array[0]) + (array[1] * array[1]) );

        if (array[0] != 0 || array[1] != 0 ){
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0,0};
    }

}
