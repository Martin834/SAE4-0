package com.example.sae4_project.Strategy;

import com.example.sae4_project.Application.AgarioApplication;
import com.example.sae4_project.Controller.AgarioController;
import com.example.sae4_project.Entity.*;

import java.util.List;

public class CollectorAI implements AIStrategy{
    @Override
    public void execute(Enemy enemy) {
        List<Pellet> pellets = AgarioController.getPellets();
        List<SpecialPellets> specialPellets = AgarioController.getSpecialPellets();
        Player player = AgarioController.getPlayer();
        Pellet closestPellet = null;
        SpecialPellets closestSpecialPellet = null;
        double minDistance1 = Double.MAX_VALUE;
        //double minDistance2 = Double.MAX_VALUE;

        if (enemy.calculateRadius() >= player.calculateRadius() * 1.33) {
            System.out.println("L'ennemi est assez grand pour devenir un Hunter !");
            enemy.setStrategy(new HunterAI());
            return;
        }

        for (Pellet pellet : pellets) {
            double distance = enemy.getDistanceTo(pellet);
            if (distance < minDistance1) {
                minDistance1 = distance;
                closestPellet = pellet;
            }
        }
        for (SpecialPellets Special : specialPellets) {
            double distance = enemy.getDistanceTo(Special);
            if (distance < minDistance1) {
                minDistance1 = distance;
                closestSpecialPellet = Special;
            }
        }
        if (closestPellet != null /*&& minDistance1 < minDistance2*/) {
            enemy.moveTowards(closestPellet.getCircle().getCenterX(), closestPellet.getCircle().getCenterY(), enemy.calculateMaxSpeed()*1.5);
        }
        else if (closestSpecialPellet != null/* && minDistance2 < minDistance1*/){
            enemy.moveTowards(closestSpecialPellet.getCircle().getCenterX(), closestSpecialPellet.getCircle().getCenterY(), enemy.calculateMaxSpeed()*4);
        }
    }

}
