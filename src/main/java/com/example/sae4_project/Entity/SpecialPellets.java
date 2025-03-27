package com.example.sae4_project.Entity;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class SpecialPellets extends Pellet {


    // Enumération des types d'effets possibles
    private enum EffectType {
        DOUBLE_SPEED,
        HALF_SPEED
    }

    private EffectType effectType;
    private Timeline effectTimeline;

    public SpecialPellets() {
        super();
        this.setMass(2);
        Random r = new Random();
        this.posX = r.nextInt(801);
        this.posY = r.nextInt(801);

        circle = new Circle(this.posX, this.posY, calculateRadius());

        this.identifier = this.posX * this.posY * r.nextInt(834);

        this.effectType = EffectType.values()[r.nextInt(EffectType.values().length)];

        if (effectType == EffectType.DOUBLE_SPEED){
            circle.setFill(Color.BLUE);
        }
        else {
            circle.setFill(Color.RED);
        }
    }

    public SpecialPellets(double x, double y) {
        super();
        this.setMass(2);
        circle = new Circle(x, y, calculateRadius());
        circle.setFill(Color.RED);
        this.effectType = EffectType.values()[new Random().nextInt(EffectType.values().length)];
        if (effectType == EffectType.DOUBLE_SPEED){
            circle.setFill(Color.BLUE);
        }
        else {
            circle.setFill(Color.RED);
        }
        //circle.setStroke(Color.BLACK);
    }

    // Calcul du rayon du SpecialPellet basé sur sa masse
    public double calculateRadius() {
        double mass = this.mass.get();
        return 10 * Math.sqrt(mass);
    }

    // Retourne le cercle associé à ce SpecialPellet
    public Circle getCircle() {
        return circle;
    }

    /*public void applyEffect(Player player) {
        switch (effectType) {
            case DOUBLE_SPEED:
                applyDoubleSpeedEffect(player);
                break;
            case HALF_SPEED:
                applyHalfSpeedEffect(player);
                break;
        }
    }*/

    public void applyEffect(MoveableBody mobile) {
        switch (effectType) {
            case DOUBLE_SPEED:
                applyDoubleSpeedEffect(mobile);
                break;
            case HALF_SPEED:
                applyHalfSpeedEffect(mobile);
                break;
        }
    }

    // Applique l'effet "double speed" (augmente la vitesse du joueur)
    private void applyDoubleSpeedEffect(MoveableBody mobile) {
        if (effectTimeline != null) {
            effectTimeline.stop();
        }

        double originalSpeed = mobile.speed;
        mobile.speed *= 2;

        effectTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            mobile.speed /= 2;
        }));

        effectTimeline.setCycleCount(1);
        effectTimeline.play();
    }



    // Applique l'effet "half speed" (réduit la vitesse du joueur de moitié)
    private void applyHalfSpeedEffect(MoveableBody mobile) {
        if (effectTimeline != null) {
            effectTimeline.stop();
        }
        mobile.speed /= 2;

        effectTimeline = new Timeline(new KeyFrame(Duration.seconds(3), e -> {
            mobile.speed *= 2;
        }));

        effectTimeline.setCycleCount(1);
        effectTimeline.play();
    }


    // Accesseur pour l'effet de ce SpecialPellet
    public EffectType getEffectType() {
        return effectType;
    }
}