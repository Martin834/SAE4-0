package com.example.sae4_project.Strategy;

import com.example.sae4_project.Controller.AgarioController;
import com.example.sae4_project.Entity.Enemy;
import com.example.sae4_project.Entity.Player;

public class HunterAI implements AIStrategy {
    @Override
    public void execute(Enemy enemy) {

        Player player = AgarioController.getPlayer();
        double minDistance = Double.MAX_VALUE;

        Player target = null;
        if (player.calculateRadius()*1.33 < enemy.calculateRadius()) { // Ne chasse que les plus petits
            double distance = enemy.getDistanceTo(player);
            if (distance < minDistance) {
                minDistance = distance;
                target = player;
            }
        }

        if (target != null) {
            enemy.moveTowards(target.getCircle().getCenterX(), target.getCircle().getCenterY());
        }
        /*else {
            enemy.setStrategy(new CollectorAI());
        }*/
    }
}
