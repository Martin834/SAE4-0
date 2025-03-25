package com.example.sae4_project.Strategy;

import com.example.sae4_project.Entity.Enemy;

public class RandomAI implements AIStrategy{
    @Override
    public void execute(Enemy enemy) {
        double randomX = Math.random() * 800;
        double randomY = Math.random() * 600;
        enemy.moveTowards(randomX, randomY);
    }
}
