package com.example.sae4_project.Strategy;

import com.example.sae4_project.Controller.AgarioController;
import com.example.sae4_project.Entity.Enemy;
import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;

import java.util.List;

public class HunterAI implements AIStrategy {
        private static final double SIZE_THRESHOLD = 1.33;  // Le facteur de taille

        @Override
        public void execute(Enemy enemy) {
            Player player = AgarioController.getPlayer();

            // Vérifier si l'ennemi doit être en mode Hunter (plus grand que le joueur)
            if (enemy.calculateRadius() >= SIZE_THRESHOLD * player.calculateRadius()) {
                Pellet closestPellet = getClosestPellet(enemy); // Chercher la pelote la plus proche
                if (closestPellet != null) {
                    // Déplace l'ennemi vers la pelote la plus proche
                    enemy.moveTowards(closestPellet.getCircle().getCenterX(), closestPellet.getCircle().getCenterY());
                }
            } else {
                // Si l'ennemi est devenu plus petit que le joueur, passe à CollectorAI
                enemy.setStrategy(new CollectorAI());
            }
        }

        // Fonction pour obtenir la pelote la plus proche
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


