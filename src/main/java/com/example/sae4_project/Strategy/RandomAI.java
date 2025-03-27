package com.example.sae4_project.Strategy;

import com.example.sae4_project.Entity.Enemy;

import java.util.Random;

public class RandomAI implements AIStrategy {
    private Random random = new Random();
    private long lastChangeTime = 0;

    @Override
    public void execute(Enemy enemy) {
        long now = System.nanoTime();
        if (now - lastChangeTime > 2_000_000_000L) {
            double angle = random.nextDouble() * 2 * Math.PI;
            enemy.speed = enemy.calculateMaxSpeed()*0.7;
            enemy.moveTowards(random.nextDouble(600), random.nextDouble(400));
            enemy.velocity[0] = Math.cos(angle) * enemy.calculateMaxSpeed();
            enemy.velocity[1] = Math.sin(angle) * enemy.calculateMaxSpeed();
            lastChangeTime = now;
        }
    }
}
