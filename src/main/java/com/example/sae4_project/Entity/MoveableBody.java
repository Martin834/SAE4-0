package com.example.sae4_project.Entity;

import com.example.sae4_project.QuadTree.Map;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.util.List;
import java.util.Random;



public abstract class MoveableBody extends Entity {

    public CircleComposite circleComposite = new CircleComposite();
    public double speed = 5;
    public double[] velocity = new double[2];
    public ArrayList<Circle> circlesList = new ArrayList<Circle>();

    /**
     * Calls the other moveTowards method
     * @param posXMouse
     * @param posYMouse
     */
    public void moveTowards(double posXMouse, double posYMouse) {
        moveTowards(posXMouse, posYMouse, this.speed);
    }

    /**
     * Checks where the cursor is on the screen and sets the velocity for x and y axis, to know how fast the
     * MoveableBody should go
     * @param posXMouse
     * @param posYMouse
     * @param maxSpeed
     */
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

    /**
     * Moves the circles of this MoveableBody in the direction that the velocity array describes, for each circle
     * contained in the MoveableBody.
     */
    public void move() {
        for (Circle circle : this.circlesList) {
            if ((circle.getCenterX() + velocity[0])>=0 && (circle.getCenterX() + velocity[0])<= Map.size) {
                circle.setCenterX(circle.getCenterX() + velocity[0]);
            }
            if ((circle.getCenterY() + velocity[1])>=0 && (circle.getCenterY() + velocity[1])<= Map.size) {
                circle.setCenterY(circle.getCenterY() + velocity[1]);
            }
        }
    }

    /**
     * Normalizes an array of doubles. Related to vector normalization.
     * @param array
     * @return
     */
    public double[] normalizeDouble(double[] array) {
        double magnitude = Math.sqrt((array[0] * array[0]) + (array[1] * array[1]));

        if (magnitude != 0) {
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0, 0};
    }

    /**
     * @param pellet
     * @return the distance from the specified Pellet parameter.
     */
    public double getDistanceTo(Pellet pellet) {
        double dx = this.circle.getCenterX() - pellet.getCircle().getCenterX();
        double dy = this.circle.getCenterY() - pellet.getCircle().getCenterY();
        return Math.sqrt(dx * dx + dy * dy);
    }


    /**
     * @param other
     * @return Circle if the MoveableBody is colliding with another, null otherwise.
     */
    public Circle isColliding(MoveableBody other) {

        for (Circle circle1 : this.circlesList) {
            double dx = circle1.getCenterX() - other.circle.getCenterX();
            double dy = circle1.getCenterY() - other.circle.getCenterY();
            double distance = Math.sqrt(dx * dx + dy * dy);
            if (distance < (circle1.getRadius() + other.circle.getRadius())) {
                return circle1;
            }
        }
        return null;

    }

    /**
     * @return the radius of this MoveableBody
     */
    public double calculateRadius() {
        double mass = this.mass.get();
        return 10 * Math.sqrt(mass);
    }

    /**
     * Makes the MoveableBody fatter according to the size of the eaten Entity (other)
     * Also does the animations.
     * @param other
     * @param circle
     */
    public void makeFatter(Entity other, Circle circle) {
        double growthFactor = 5.0;
        double otherMass = other.getMass();
        double myMass = this.getMass();
        double newMassAnim = myMass + otherMass;
        double newMass = circle.getRadius() * circle.getRadius() + (other.getMass() * growthFactor)*2;

        double scaleFactor = Math.sqrt(newMassAnim / myMass);

        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), circle);
        scaleTransition.setToX(scaleFactor);
        scaleTransition.setToY(scaleFactor);
        scaleTransition.setInterpolator(Interpolator.EASE_OUT);

        FillTransition fillTransition = new FillTransition(Duration.millis(150), circle);
        fillTransition.setFromValue(Color.rgb(red,green,blue));
        fillTransition.setToValue(Color.rgb(red,green,blue));
        fillTransition.setCycleCount(1);
        fillTransition.setAutoReverse(true);

        scaleTransition.setOnFinished(event -> {
            this.setMass(this.getMass() + (other.getMass() * growthFactor));
            circle.setRadius(Math.sqrt(newMass));
            circle.setScaleX(1);
            circle.setScaleY(1);
        });

        random.setSeed(random.nextInt());
        scaleTransition.play();
        fillTransition.play();

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
}


