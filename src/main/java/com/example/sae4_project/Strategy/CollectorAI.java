package com.example.sae4_project.Strategy;

import com.example.sae4_project.Application.AgarioApplication;
import com.example.sae4_project.Controller.AgarioController;
import com.example.sae4_project.Entity.Enemy;
import com.example.sae4_project.Entity.Entity;
import com.example.sae4_project.Entity.Pellet;

import java.util.List;

public class CollectorAI implements AIStrategy{
    @Override
    public void execute(Enemy enemy) {
        List<Pellet> pellets = AgarioController.getPellets();
        Pellet closestPellet = null;
        double minDistance = Double.MAX_VALUE;

        for (Pellet pellet : pellets) {
            double distance = enemy.getDistanceTo(pellet);
            if (distance < minDistance) {
                minDistance = distance;
                closestPellet = pellet;
            }
        }

        if (closestPellet != null) {
            enemy.moveTowards(closestPellet.getCircle().getCenterX(), closestPellet.getCircle().getCenterY());
        }
    }
}
