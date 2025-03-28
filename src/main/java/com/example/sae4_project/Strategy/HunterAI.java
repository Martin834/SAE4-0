package com.example.sae4_project.Strategy;

import com.example.sae4_project.Controller.AgarioController;
import com.example.sae4_project.Entity.Enemy;
import com.example.sae4_project.Entity.Player;
import javafx.scene.shape.Circle;

public class HunterAI implements AIStrategy {
    private static final double SIZE_THRESHOLD = 1.33;

    /**
     * Executes the Hunter behavior strategy for the AIs. If the bot is 33% bigger than the player, it will chase him.
     * @param enemy
     */
    @Override
    public void execute(Enemy enemy) {
        Player player = AgarioController.getPlayer();

        if (enemy.calculateRadius() >= SIZE_THRESHOLD * player.calculateRadius()) {

            for (Circle circle : player.getCirclesList()) {
                double playerX = circle.getCenterX();
                double playerY = circle.getCenterY();
                enemy.moveTowards(playerX, playerY, enemy.calculateMaxSpeed());
            }

        } else {
            enemy.setStrategy(new CollectorAI());
        }
    }
}


