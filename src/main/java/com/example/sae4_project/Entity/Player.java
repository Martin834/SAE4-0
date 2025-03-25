package com.example.sae4_project.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Map;

public class Player extends Entity {

    private double speed;
    private double radius;
    private Circle circle;
    private double posX;
    private double posY;

    public Player() {
        this.radius=25;
        this.posX = 400-this.radius/2;
        this.posY = 300-this.radius/2;

        Circle circle = new Circle(this.posX, this.posY, this.radius);
        circle.setFill(Color.RED);
        this.circle = circle;
    }

    public void moveTowards(double posXMouse, double posYMouse) {
        if (posXMouse > this.posX) {
            this.posX++;
            this.circle.setCenterX(this.posX);
        }
        else if (posXMouse < this.posX) {
            this.posX--;
            this.circle.setCenterX(this.posX);
        }
        if (posYMouse > this.posY) {
            this.posY++;
            this.circle.setCenterY(this.posY);
        }
        else if (posYMouse < this.posY) {
            this.posY--;
            this.circle.setCenterY(this.posY);
        }
    }


    public Circle getCircle() {
        return this.circle;
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
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
