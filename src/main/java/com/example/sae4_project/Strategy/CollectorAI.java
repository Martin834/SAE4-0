package com.example.sae4_project.Strategy;

import com.example.sae4_project.Entity.Enemy;
import com.example.sae4_project.Entity.Entity;

public class CollectorAI implements AIStrategy{
    @Override
    public void execute(Enemy enemy) {
        Entity closestFood = enemy.findClosestFood();
        if (closestFood != null) {
            enemy.moveTowards(closestFood.circle.getCenterX(), closestFood.circle.getCenterY());
    }
}}
