package com.example.sae4_project.Entity;

import com.example.sae4_project.QuadTree.Coordinate;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class Player extends MoveableBody {

    public Player() {
        super();
        this.setMass(5);
        circle = new Circle(400-this.getMass()/2, 300-this.getMass()/2, 25);
        circle.setFill(Color.RED);
        this.circlesList.add(circle);
    }


    public Pellet detectPellet(ArrayList<Pellet> all) {
        for (Circle circle : this.circlesList) {
            for (Pellet pellet : all) {
                Shape intersect = Circle.intersect(circle, pellet.getCircle());
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    return pellet;
                }
            }
        }
        return null;
    }

    public ArrayList<Circle> getCirclesList() {
        return this.circlesList;
    }

    public double calculateRadius() {
        return 10 * Math.sqrt(this.getMass());
    }

    public double calculateMaxSpeed() {
        return (1 / this.massProperty().doubleValue()) * 60;
    }

    public void divideItself() {
        ArrayList<Circle> tempCirclesList = new ArrayList<>(this.circlesList);
        for (Circle circle : tempCirclesList) {
            circle.setRadius(circle.getRadius()/2);
            this.
            Circle circle2 = new Circle(circle.getCenterX() + 20, circle.getCenterY() + 20, circle.getRadius());
            circle2.setFill(circle.getFill());
            circlesList.add(circle2);
        }
        System.out.println(circlesList.toString());
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
        this.setMass(this.getMass() +  entity.getMass());
        double radius = this.calculateRadius();
        this.circle.setRadius(radius);
    }
}
