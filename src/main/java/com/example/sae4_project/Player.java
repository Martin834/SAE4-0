package com.example.sae4_project;

import java.util.Map;

public class Player extends Entity {

    private double speed;
    private double radius;

    public Player() {

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
