package com.example.sae4_project.Strategy;

import com.example.sae4_project.Entity.Enemy;

import java.util.Random;

public class RandomAI implements AIStrategy {
    private Random random = new Random();
    private double dx, dy;
    private long lastChangeTime = 0;

    @Override
    public void execute(Enemy enemy) {
        long now = System.nanoTime();
        if (now - lastChangeTime > 2_000_000_000L) { // Change de direction toutes les 2 secondes
            double angle = random.nextDouble() * 2 * Math.PI;
            double speed = 1 + random.nextDouble() * 2;
            dx = Math.cos(angle) * speed;
            dy = Math.sin(angle) * speed;
            lastChangeTime = now;
        }

        enemy.moveTowards(enemy.getCircle().getCenterX() + dx, enemy.getCircle().getCenterY() + dy);
    }
}
