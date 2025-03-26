package com.example.sae4_project.Entity;

import com.example.sae4_project.Strategy.AIStrategy;
import com.example.sae4_project.Strategy.CollectorAI;
import com.example.sae4_project.Strategy.HunterAI;
import com.example.sae4_project.Strategy.RandomAI;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends MoveableBody {
    private AIStrategy strategy;
    private long lastUpdateTime = 0;

    public Enemy(double x, double y) {
        super();
        circle = new Circle(x,y, 25);
        Random rand = new Random();
        int r = rand.nextInt(256);
        int g = rand.nextInt(256);
        int b = rand.nextInt(256);
        circle.setFill(Color.rgb(r, g, b));
        assignRandomStrategy();
        //this.strategy = new RandomAI();
    }

    public Circle getCircle() {
        return circle;
    }
    public void setStrategy(AIStrategy strategy) {
        this.strategy = strategy;
    }

    public AIStrategy getStrategy() {
        return this.strategy;
    }

    public void assignRandomStrategy() {
        Random random = new Random();
        int choice = random.nextInt(3); // 0, 1 ou 2

        switch (choice) {
            case 0 -> strategy = new RandomAI();
            case 1 -> strategy = new HunterAI();
            case 2 -> strategy = new CollectorAI();
        }
    }

    public double calculateRadius() {
        double mass = this.mass.get();
        return 10 * Math.sqrt(mass);
    }

    public double calculateMaxSpeed() {
        return (1 / this.massProperty().doubleValue()) * 60;
    }
    public void executeStrategy(long now) {
        if (strategy != null && now - lastUpdateTime > 500_000_000L) { // 500ms entre chaque update
            strategy.execute(this);
            lastUpdateTime = now;
        }
    }

    public Pellet detectPellet(List<Pellet> allPellets) {
        Pellet closestPellet = null;
        double minDistance = Double.MAX_VALUE;

        // Parcourez les pelotes et vérifiez seulement celles qui sont proches de l'ennemi
        for (Pellet pellet : allPellets) {
            // Calculer la distance entre l'ennemi et la pelote
            double distance = getDistanceTo(pellet);

            // Si l'ennemi est proche de la pelote, on garde la pelote la plus proche
            if (distance < minDistance && distance <= circle.getRadius()) {
                minDistance = distance;
                closestPellet = pellet;
            }
        }

        // Si une pelote est à une distance acceptable, la retourner
        return closestPellet;
    }

    public void makeFatter(Pellet pellet) {
        // Vérifiez que la taille de l'ennemi augmente correctement
        double pelletSize = pellet.getMass();  // Taille de la pelote
        if (pelletSize > 0) {
            // Augmenter le rayon de l'ennemi directement via le Circle
            double newRadius = circle.getRadius() + pelletSize;
            circle.setRadius(newRadius); // Mise à jour du rayon de l'ennemi
        }
    }
    /*public void makeFatter(MoveableBody other) {
        double otherMass = Math.PI * Math.pow(other.circle.getRadius(), 2); // Surface du cercle
        double myMass = Math.PI * Math.pow(this.circle.getRadius(), 2);

        double newMass = myMass + otherMass; // On additionne les masses
        double newRadius = Math.sqrt(newMass / Math.PI); // Conversion en rayon

        this.circle.setRadius(newRadius); // Mise à jour du rayon
    }*/




}