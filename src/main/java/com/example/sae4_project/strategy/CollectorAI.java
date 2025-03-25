package com.example.sae4_project.strategy;

import com.example.sae4_project.Application.AgarioApplication;
//import com.example.sae4_project.Entity.Enemy;
//import com.example.sae4_project.Entity.GameWorld;
import com.example.sae4_project.Entity.Enemy;
import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;

import java.util.List;

public class CollectorAI implements AIBehavior{

    @Override
    public void execute(Enemy enemy) {
        return;
        /*
        List<Pellet> pellets = AgarioApplication.getPelletList();

        Pellet closestPellet = pellets.get(0);
        double minDist = enemy.distanceTo(closestPellet.getPosition());

        for (Pellet p : pellets) {
            double dist = enemy.distanceTo(p.getPosition());
            if (dist < minDist) {
                minDist = dist;
                closestPellet = p;
            }
        }

        double[] direction = {
                closestPellet.getSprite().getCenterX() - enemy.getSprite().getCenterX(),
                closestPellet.getSprite().getCenterY() - enemy.getSprite().getCenterY()
        };
        enemy.moveToward(direction);*/
    }

}
