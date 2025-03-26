package com.example.sae4_project.Entity;

import com.example.sae4_project.Strategy.AIStrategy;
import com.example.sae4_project.Strategy.CollectorAI;
import com.example.sae4_project.Strategy.HunterAI;
import com.example.sae4_project.Strategy.RandomAI;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Enemy extends MoveableBody{
    private double closestEntityDistance;
    private Entity closestEntity;
    private AIStrategy strategy;

    public Enemy() {
        super();
        circle = new Circle(400-25/2, 300-25/2, 25);
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        circle.setFill(Color.rgb(r,g,b));
        assignRandomStrategy();
    }

    public Circle getCircle() {
        return circle;
    }

    public double calculateRadius() {
        return 10 * Math.sqrt(this.mass);
    }

    private void assignRandomStrategy() {
        Random random = new Random();
        int choice = random.nextInt(2); // 0, 1 ou 2

        switch (choice) {
            case 0 -> strategy = new RandomAI();
            case 1 -> strategy = new HunterAI();
            //case 2 -> strategy = new CollectorAI();
        }
    }

    public void setStrategy(AIStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy() {
        if (strategy != null) {
            strategy.execute(this);
        }
    }
}
