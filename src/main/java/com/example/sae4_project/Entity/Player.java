package com.example.sae4_project.Entity;

import com.example.sae4_project.QuadTree.Coordinate;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Map;

public class Player extends MoveableBody {


    public Player() {
        super();
        this.setMass(3);
        circle = new Circle(400-this.getMass()/2, 300-this.getMass()/2, calculateRadius());
        circle.setFill(Color.BLACK);
    }


    public Pellet detectPellet(ArrayList<Pellet> all) {
        double test = this.circle.getCenterX();
        for (Pellet pellet : all) {
            Shape intersect = Circle.intersect(this.circle, pellet.getCircle());
            if (intersect.getBoundsInLocal().getWidth() != -1) {
                return pellet;
            }
        }
        return null;
    }

    public SpecialPellets detectSpecialPellet(ArrayList<SpecialPellets> all) {
        double test = this.circle.getCenterX();
        for (SpecialPellets Spellet : all) {
            Shape intersect = Circle.intersect(this.circle, Spellet.getCircle());
            if (intersect.getBoundsInLocal().getWidth() != -1) {
                return Spellet;
            }
        }
        return null;
    }

    public Circle getCircle() {
        return circle;
    }

    public double calculateRadius() {
        return 10 * Math.sqrt(this.getMass());
    }

    public double calculateMaxSpeed() {
        return (1 / this.massProperty().doubleValue()) * 60;
    }
}
