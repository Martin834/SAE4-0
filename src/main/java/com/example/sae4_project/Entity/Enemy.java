package com.example.sae4_project.Entity;

import com.example.sae4_project.Strategy.AIStrategy;
import com.example.sae4_project.Strategy.CollectorAI;
import com.example.sae4_project.Strategy.HunterAI;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Enemy extends MoveableBody{
    private double closestEntityDistance;
    private Entity closestEntity;
    private AIStrategy strategy;

    public Enemy() {
        super();
        circle = new Circle(400-25/2, 300-25/2, 25);
        circle.setFill(Color.BLUE);
        strategy = new HunterAI();
    }

    public void setStrategy(AIStrategy strategy){
        this.strategy = strategy;
    }

    public Entity findClosestFood() {
        return new Entity() {
        };
    }
}
