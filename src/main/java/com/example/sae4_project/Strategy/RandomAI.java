package com.example.sae4_project.Strategy;

import com.example.sae4_project.Entity.Enemy;

import java.util.Random;

public class RandomAI implements AIStrategy{
    private Random random = new Random();
    @Override
    public void execute(Enemy enemy) {
        double angle = random.nextDouble() * 2 * Math.PI;
        double speed = 1 + random.nextDouble() * 2;
        double dx = Math.cos(angle) * speed;
        double dy = Math.sin(angle) * speed;

        enemy.moveTowards(enemy.getCircle().getCenterX() + dx, enemy.getCircle().getCenterY() + dy);
    }
}
