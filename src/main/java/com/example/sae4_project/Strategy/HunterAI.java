package com.example.sae4_project.Strategy;

import com.example.sae4_project.Controller.AgarioController;
import com.example.sae4_project.Entity.Enemy;
import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;

import java.util.List;

public class HunterAI implements AIStrategy {
        private static final double SIZE_THRESHOLD = 1.33;

        @Override
        public void execute(Enemy enemy) {
            Player player = AgarioController.getPlayer();

            if (enemy.calculateRadius() >= SIZE_THRESHOLD * player.calculateRadius()) {

                double playerX = player.getCircle().getCenterX();
                double playerY = player.getCircle().getCenterY();

                enemy.moveTowards(playerX, playerY);
            } else {
                enemy.setStrategy(new CollectorAI());
            }
        }

        private Pellet getClosestPellet(Enemy enemy) {
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
            return closestPellet;
        }
    }


