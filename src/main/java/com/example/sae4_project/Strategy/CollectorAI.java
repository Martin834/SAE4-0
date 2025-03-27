package com.example.sae4_project.Strategy;

import com.example.sae4_project.Application.AgarioApplication;
import com.example.sae4_project.Controller.AgarioController;
import com.example.sae4_project.Entity.Enemy;
import com.example.sae4_project.Entity.Entity;
import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;

import java.util.List;

public class CollectorAI implements AIStrategy{
    @Override
    public void execute(Enemy enemy) {
        List<Pellet> pellets = AgarioController.getPellets();
        Player player = AgarioController.getPlayer();
        Pellet closestPellet = null;
        double minDistance = Double.MAX_VALUE;

        if (enemy.calculateRadius() >= player.calculateRadius() * 1.33) {
            System.out.println("L'ennemi est assez grand pour devenir un Hunter !");
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
            //System.out.println("Ennemi se dirige vers un pellet !");
            enemy.moveTowards(closestPellet.getCircle().getCenterX(), closestPellet.getCircle().getCenterY());
        }
    }

}
