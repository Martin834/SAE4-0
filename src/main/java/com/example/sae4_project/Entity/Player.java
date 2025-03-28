package com.example.sae4_project.Entity;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import java.util.ArrayList;
import java.util.List;

public class Player extends MoveableBody {

    /**
     * Player object constructor. Gives it a random color and creates of circle for it according to its mass, and a
     * radius of 15. Assigns it a CircleComposite object so that it can hold other CircleLeaf objects and be divided.
     */
    public Player() {
        super();
        this.setMass(5);
        Circle circle = new Circle(400 - this.getMass() / 2, 300 - this.getMass() / 2, 15);
        circlesList.add(circle);
        circle.setFill(Color.RED);
        this.circleComposite = new CircleComposite();
        this.circleComposite.add(new CircleLeaf(circle));
    }

    /**
     * For each pellet, detects if one the player's circles touch one.
     * @param all
     * @return true if one the circles of the player touches a pellet, false otherwise.
     */
    public Pellet detectPellet(ArrayList<Pellet> all) {
        for (Circle circle : circleComposite.getCircles()) {
            for (Pellet pellet : all) {
                Shape intersect = Circle.intersect(circle, pellet.getCircle());
                if (intersect.getBoundsInLocal().getWidth() != -1) {
                    return pellet;
                }
            }
        }
        return null;
    }

    /**
     * @return the Player's circlesList
     */
    public List<Circle> getCirclesList() {
        return circleComposite.getCircles();
    }

    /**
     * @return the calculated radius
     */
    public double calculateRadius() {
        return 10 * Math.sqrt(this.getMass());
    }

    /**
     * @return the calculated max speed
     */
    public double calculateMaxSpeed() {
        return (1 / this.massProperty().doubleValue()) * 10000;
    }

    /**
     * Divides the player into two circles.
     */
    public void divideItself() {
        List<CircleComponent> tempCirclesList = new ArrayList<CircleComponent>();
        List<CircleComponent> temptempCirclesList = new ArrayList<CircleComponent>();

        if (circleComposite.getCircles().size() >= 2) {
            this.circleComposite.getCircles().remove(1);
        }
        for (Circle circle : circleComposite.getCircles()) {
            if (circle.getRadius() >= 10) {
                tempCirclesList.add(new CircleLeaf(circle));
            }
        }
        if (tempCirclesList.isEmpty()) {
            return;
        }
        for (Circle circle : circleComposite.getCircles()) {
            if (circle.getRadius()<10){
                temptempCirclesList.add(new CircleLeaf(circle));
            }
        }

        circleComposite = new CircleComposite();
        double angleIncrement = 2 * Math.PI / tempCirclesList.size();

        for (int i = 0; i < tempCirclesList.size(); i++) {
            CircleComponent component = tempCirclesList.get(i);
            Circle originalCircle = component.getCircles().get(0);
            double newRadius = originalCircle.getRadius() / 2;
            originalCircle.setRadius(newRadius);

            double angle = i * angleIncrement;
            double newX = originalCircle.getCenterX() + Math.cos(angle) * (newRadius * 2);
            double newY = originalCircle.getCenterY() + Math.sin(angle) * (newRadius * 2);

            Circle newCircle = new Circle(newX, newY, newRadius);
            newCircle.setFill(originalCircle.getFill());

            circleComposite.add(new CircleLeaf(originalCircle));
            circleComposite.add(new CircleLeaf(newCircle));
            this.circlesList.add(newCircle);
            for (CircleComponent circle : temptempCirclesList) {
                circleComposite.add(circle);
            }
        }
        adjustCirclePositions();
    }

    /**
     * Adjusts the positions of the circles so they don't get in the way of each other.
     */
    private void adjustCirclePositions() {
        List<Circle> circles = circleComposite.getCircles();
        for (int i = 0; i < circles.size(); i++) {
            for (int j = i + 1; j < circles.size(); j++) {
                Circle circle1 = circles.get(i);
                Circle circle2 = circles.get(j);
                double distance = Math.sqrt(Math.pow(circle1.getCenterX() - circle2.getCenterX(), 2) +
                        Math.pow(circle1.getCenterY() - circle2.getCenterY(), 2));
                double minDistance = circle1.getRadius() + circle2.getRadius();
                if (distance < minDistance) {
                    double overlap = minDistance - distance;
                    double angle = Math.atan2(circle2.getCenterY() - circle1.getCenterY(), circle2.getCenterX() - circle1.getCenterX());
                    circle1.setCenterX(circle1.getCenterX() - Math.cos(angle) * overlap / 2);
                    circle1.setCenterY(circle1.getCenterY() - Math.sin(angle) * overlap / 2);
                    circle2.setCenterX(circle2.getCenterX() + Math.cos(angle) * overlap / 2);
                    circle2.setCenterY(circle2.getCenterY() + Math.sin(angle) * overlap / 2);
                }
            }
        }
    }

    /**
     * Calls the moveTowards method of this player's CircleComposite attribute
     * @param posXMouse
     * @param posYMouse
     * @param maxSpeed
     */
    @Override
    public void moveTowards(double posXMouse, double posYMouse, double maxSpeed) {
        circleComposite.moveTowards(posXMouse, posYMouse, maxSpeed);
    }

    /**
     * Calls the move method of this player's CircleComposite attribute and adjustCirclePositions()
     */
    @Override
    public void move() {
        circleComposite.move();
        adjustCirclePositions();
    }

    /**
     * Reunites the divided circles
     * @param circle
     * @return
     */
    public Circle rassembling(ArrayList<Circle> circle) {
        Circle c = circle.get(circle.size()-1);
        circle.get(0).setRadius(10*Math.sqrt((this.circlesList.get(0).getRadius() *
                this.circlesList.get(0).getRadius())/100+(c.getRadius()*c.getRadius())/100));

        return c;
    }
}
