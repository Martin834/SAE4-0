package com.example.sae4_project.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Map;

public class Player extends MoveableBody {

    public Player() {
        super();
        Circle circle = new Circle(400-25/2, 300-25/2, 25);
        circle.setFill(Color.RED);

    }


    public Circle getCircle() {
        return circle;
    }

    public double calculateRadius() {
        return 10 * Math.sqrt(this.mass);
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
