package com.example.sae4_project.Strategy;

import com.example.sae4_project.Controller.AgarioController;
import com.example.sae4_project.Entity.Enemy;
import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;

import java.util.List;

public class CollectorAI implements AIStrategy{
    /**
     * Executes the Collector behavior strategy for the AIs. They look for the closest pellets to eat.
     * @param enemy
     */
    @Override
    public void execute(Enemy enemy) {
        List<Pellet> pellets = AgarioController.getPellets();
        Player player = AgarioController.getPlayer();
        Pellet closestPellet = null;
        double minDistance = Double.MAX_VALUE;

        if (enemy.calculateRadius() >= player.calculateRadius() * 1.33) {
            enemy.setStrategy(new HunterAI());
            return;
        }

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
