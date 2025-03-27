package com.example.sae4_project.Entity;

import com.example.sae4_project.QuadTree.Map;
import javafx.animation.*;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public abstract class MoveableBody extends Entity {

    public double speed = 5;
    public double smoothing = 80;
    public double[] velocity = new double[2];

    public void moveTowards(double posXMouse, double posYMouse) {
        moveTowards(posXMouse, posYMouse, this.calculateMaxSpeed());
    }

    public void moveTowards(double posXMouse, double posYMouse, double maxSpeed) {
        velocity = new double[]{posXMouse - this.circle.getCenterX(), posYMouse - this.circle.getCenterY()};

        double euclidianDistance = Math.sqrt((velocity[0] * velocity[0]) + (velocity[1] * velocity[1]));
        double adjustedSpeed = Math.min(euclidianDistance / 100, this.calculateMaxSpeed());


        if (euclidianDistance > 4) {
            euclidianDistance = 4 * speed;
        }

        velocity = normalizeDouble(velocity);

        velocity[0] *= adjustedSpeed;
        velocity[1] *= adjustedSpeed;
    }
    public double calculateMaxSpeed() {
        return (1 / this.massProperty().doubleValue()) * 700;
    }


    public void move() {
        if(this.circle.getCenterX() + velocity[0] >= 0 && this.circle.getCenterX() + velocity[0] <= Map.size) {
            this.circle.setCenterX(this.circle.getCenterX() + velocity[0]);
        }
        if(this.circle.getCenterY() + velocity[1] >= 0 && this.circle.getCenterY() + velocity[1] <= Map.size){
            this.circle.setCenterY(this.circle.getCenterY() + velocity[1]);
        }
    }


    public double[] normalizeDouble(double[] array) {
        double magnitude = Math.sqrt((array[0] * array[0]) + (array[1] * array[1]));

        if (magnitude != 0) {
            return new double[]{array[0] / magnitude, array[1] / magnitude};
        }
        return new double[]{0, 0};
    }

    public double getDistanceTo(Pellet pellet) {
        double dx = this.circle.getCenterX() - pellet.getCircle().getCenterX();
        double dy = this.circle.getCenterY() - pellet.getCircle().getCenterY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getDistanceTo(SpecialPellets pellet) {
        double dx = this.circle.getCenterX() - pellet.getCircle().getCenterX();
        double dy = this.circle.getCenterY() - pellet.getCircle().getCenterY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getDistanceTo(Player player) {
        double dx = this.circle.getCenterX() - player.getCircle().getCenterX();
        double dy = this.circle.getCenterY() - player.getCircle().getCenterY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public boolean isColliding(MoveableBody other) {
        double dx = this.circle.getCenterX() - other.circle.getCenterX();
        double dy = this.circle.getCenterY() - other.circle.getCenterY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (this.circle.getRadius() + other.circle.getRadius());
    }
    public double calculateRadius() {
        double mass = this.mass.get();
        return 10 * Math.sqrt(mass);
    }

    public void makeFatter(Entity other) {
        if (other == null) return;
        double otherMass = other.getMass();
        double myMass = this.getMass();
        double newMass = myMass + otherMass;
        double finalRadius = Math.sqrt(newMass);
        double scaleFactor = Math.sqrt(newMass / myMass);

        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), this.circle);
        scaleUp.setToX(scaleFactor);
        scaleUp.setToY(scaleFactor);
        scaleUp.setInterpolator(Interpolator.EASE_OUT);

        FillTransition fillTransition = new FillTransition(Duration.millis(150), circle);
        fillTransition.setFromValue(Color.rgb(red, green, blue));
        fillTransition.setToValue(Color.rgb(red, green, blue));
        fillTransition.setCycleCount(1);
        fillTransition.setAutoReverse(true);

        scaleUp.setOnFinished(event -> {
            this.setMass(newMass);
            this.circle.setScaleX(1);
            this.circle.setScaleY(1);
            this.circle.setRadius(finalRadius);

            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), this.circle);
            scaleDown.setToX(1);
            scaleDown.setToY(1);
            scaleDown.setInterpolator(Interpolator.EASE_OUT);
            scaleDown.play();
            this.circle.setRadius(calculateRadius());
        });

        scaleUp.play();
        fillTransition.play();
    }

    public void makeFatter(SpecialPellets other) {
        double otherMass = other.getMass()*5;
        double myMass = this.getMass();
        double newMass = myMass + otherMass;
        double finalRadius = Math.sqrt(newMass);
        double scaleFactor = Math.sqrt(newMass / myMass);

        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), this.circle);
        scaleUp.setToX(scaleFactor);
        scaleUp.setToY(scaleFactor);
        scaleUp.setInterpolator(Interpolator.EASE_OUT);

        FillTransition fillTransition = new FillTransition(Duration.millis(150), circle);
        fillTransition.setFromValue(Color.rgb(red, green, blue));
        fillTransition.setToValue(Color.rgb(red, green, blue));
        fillTransition.setCycleCount(1);
        fillTransition.setAutoReverse(true);

        scaleUp.setOnFinished(event -> {
            this.setMass(newMass);
            this.circle.setScaleX(1);
            this.circle.setScaleY(1);
            this.circle.setRadius(finalRadius);

            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), this.circle);
            scaleDown.setToX(1);
            scaleDown.setToY(1);
            scaleDown.setInterpolator(Interpolator.EASE_OUT);
            scaleDown.play();
            this.circle.setRadius(calculateRadius());
        });

        scaleUp.play();
        fillTransition.play();
    }

}


