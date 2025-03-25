package com.example.sae4_project.strategy;

import com.example.sae4_project.Entity.Enemy;

import java.util.Random;

public class RandomAI implements AIBehavior{
    private final Random random = new Random();
    @Override
    public void execute(Enemy enemy) {

        /*
        double angle = random.nextDouble() * 2 * Math.PI;
        double[] velocity = {Math.cos(angle), Math.sin(angle)};
        enemy.moveToward(velocity);
        */

    }
}
