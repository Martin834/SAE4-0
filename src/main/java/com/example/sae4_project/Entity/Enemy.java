package com.example.sae4_project.Entity;

import com.example.sae4_project.Strategy.AIStrategy;
import com.example.sae4_project.Strategy.CollectorAI;
import com.example.sae4_project.Strategy.HunterAI;
import com.example.sae4_project.Strategy.RandomAI;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Enemy extends MoveableBody {
    private AIStrategy strategy;
    private long lastUpdateTime = 0;

    public Enemy() {
        super();
        circle = new Circle(400 - 25 / 2, 300 - 25 / 2, 25);
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        circle.setFill(Color.rgb(r, g, b));
        assignRandomStrategy();
        //this.strategy = new RandomAI();
    }

    public Circle getCircle() {
        return circle;
    }
    public void setStrategy(AIStrategy strategy) {
        this.strategy = strategy;
    }

    public AIStrategy getStrategy() {
        return this.strategy;
    }

    public void assignRandomStrategy() {
        Random random = new Random();
        int choice = random.nextInt(3); // 0, 1 ou 2

        switch (choice) {
            case 0 -> strategy = new RandomAI();
            case 1 -> strategy = new HunterAI();
            case 2 -> strategy = new CollectorAI();
        }
    }

    public double calculateRadius() {
        return 10 * Math.sqrt(this.mass);
    }
    public void executeStrategy(long now) {
        if (strategy != null && now - lastUpdateTime > 500_000_000L) { // 500ms entre chaque update
            strategy.execute(this);
            lastUpdateTime = now;
        }
    }}
