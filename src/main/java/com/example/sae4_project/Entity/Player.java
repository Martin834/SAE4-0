package com.example.sae4_project.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Map;

public class Player extends MoveableBody {

    public Player() {
        super();
        this.mass = 5;
        circle = new Circle(400-this.mass/2, 300-this.mass/2, 25);
        circle.setFill(Color.RED);
    }

    public Pellet detectPellet(ArrayList<Pellet> all) {
        double test = this.circle.getCenterX();
        for(Pellet pellet : all ){
            Shape intersect = Circle.intersect(this.circle, pellet.getCircle());
            if (intersect.getBoundsInLocal().getWidth() != -1) {
                return pellet;
            }
        }
        return null;
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

    public void makeFatter(Entity entity) {
        System.out.println(entity.mass+","+this.mass);
        this.mass += entity.mass;
        double radius = this.calculateRadius();
        this.circle.setRadius(radius);
    }
}
