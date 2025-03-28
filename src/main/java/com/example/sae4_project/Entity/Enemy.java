package com.example.sae4_project.Entity;

import com.example.sae4_project.Strategy.AIStrategy;
import com.example.sae4_project.Strategy.CollectorAI;
import com.example.sae4_project.Strategy.HunterAI;
import com.example.sae4_project.Strategy.RandomAI;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends MoveableBody {
    private AIStrategy strategy;
    private long lastUpdateTime = 0;

    /**
     * Enemy constructor, gives them and random color, a mass of 5, and assigns them a random behavior strategy
     * @param x
     * @param y
     */
    public Enemy(double x, double y) {
        super();
        this.setMass(5);
        circle = new Circle(x,y,calculateRadius());
        circlesList.add(circle);
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        circle.setFill(Color.rgb(r, g, b));
        assignRandomStrategy();
    }

    /**
     *
     * @return this instance's circle object
     */
    public Circle getCircle() {
        return circle;
    }

    /**
     * Sets the behavior strategy of this instance to the specified strategy
     * @param strategy
     */
    public void setStrategy(AIStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     *
     * @return this instance's behavior strategy
     */
    public AIStrategy getStrategy() {
        return this.strategy;
    }

    /**
     * Assign a random behavior strategy to this instance.
     */
    public void assignRandomStrategy() {
        Random random = new Random();
        int choice = random.nextInt(3); // 0, 1 ou 2

        switch (choice) {
            case 0 -> strategy = new RandomAI();
            case 1 -> strategy = new HunterAI();
            case 2 -> strategy = new CollectorAI();
        }
    }

    /**
     * Calculates the radius of this instance
     * @return
     */
    public double calculateRadius() {
        double mass = this.mass.get();
        return 10 * Math.sqrt(mass);
    }

    /**
     * Calculates the max speed of this instance
     * @return
     */
    public double calculateMaxSpeed() {
        return (1 / this.massProperty().doubleValue()) * 60;
    }

    /**
     * Execute the strategy stored in this instance
     * @param now
     */
    public void executeStrategy(long now) {
        if (strategy != null && now - lastUpdateTime > 500_000_000L) { // 500ms entre chaque update
            strategy.execute(this);
            lastUpdateTime = now;
        }
    }

    /**
     * Detects the closest pellest to the enemy
     * @param allPellets
     * @return
     */
    public Pellet detectPellet(List<Pellet> allPellets) {
        Pellet closestPellet = null;
        double minDistance = Double.MAX_VALUE;

        for (Pellet pellet : allPellets) {
            double distance = getDistanceTo(pellet);

            if (distance < minDistance && distance <= circle.getRadius()) {
                minDistance = distance;
                closestPellet = pellet;
            }
        }
        return closestPellet;
    }

}