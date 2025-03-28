package com.example.sae4_project.Strategy;

import com.example.sae4_project.Entity.Enemy;

import java.util.Random;

public class RandomAI implements AIStrategy {
    private Random random = new Random();
    private long lastChangeTime = 0;

    /**
     * Executes the Random behavior strategy for the AIs. They go in a random direction every 2 seconds.
     * @param enemy
     */
    @Override
    public void execute(Enemy enemy) {
        long now = System.nanoTime();
        if (now - lastChangeTime > 2_000_000_000L) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double speed = enemy.calculateMaxSpeed();
            enemy.velocity[0] = Math.cos(angle) * speed;
            enemy.velocity[1] = Math.sin(angle) * speed;
            lastChangeTime = now;
        }
    }
}
