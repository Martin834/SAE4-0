package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.*;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.*;

public class AgarioController implements Initializable {
    @FXML
    private Pane terrain;
    private List<Enemy> enemies = new ArrayList<>();
    private Map<Enemy, double[]> enemyVelocities = new HashMap<>();


    private Player player;

    private double posX;
    private double posY;

    @FXML
    private void addCircle(Circle circle) {

        this.terrain.getChildren().add(circle);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Random r = new Random();

        // Création et ajout des ennemis
        for (int i = 0; i < 10; i++) {
            Enemy enemy = new CreatorEnemy().create();
            Circle circleEnemy = enemy.getCircle();
            addCircle(circleEnemy);

            // Position aléatoire au spawn
            double randX = r.nextDouble() * 800;
            double randY = r.nextDouble() * 600;
            enemy.getCircle().setCenterX(randX);
            enemy.getCircle().setCenterY(randY);

            // Direction aléatoire (vitesse X et Y)
            double angle = r.nextDouble() * 2 * Math.PI;
            double speed = 1 + r.nextDouble() * 2; // Entre 1 et 3 pixels/frame
            enemyVelocities.put(enemy, new double[]{Math.cos(angle) * speed, Math.sin(angle) * speed});

            enemies.add(enemy);
        }

        // Création des pastilles
        for (int i = 0; i < 100; i++) {
            Pellet pellet = new CreatorPellet().create();
            Circle circlePellet = pellet.getCircle();
            addCircle(circlePellet);
        }

        // Création du joueur
        this.player = new CreatorPlayer().create();
        Circle circle = this.player.getCircle();
        addCircle(circle);

        // Capture des mouvements de la souris
        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, handler);

        // Lancement de la boucle du jeu
        this.gameLoop();
    }



    EventHandler handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            posX = mouseEvent.getX();
            posY = mouseEvent.getY();

        }
    };

    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                // Déplacement du joueur vers la souris
                player.moveTowards(posX, posY);

                // Mise à jour des ennemis
                for (Enemy enemy : enemies) {
                    double[] velocity = enemyVelocities.get(enemy);
                    if (velocity == null) continue;

                    double newX = enemy.getCircle().getCenterX() + velocity[0];
                    double newY = enemy.getCircle().getCenterY() + velocity[1];

                    // Vérifier les collisions avec les bords et inverser la direction si nécessaire
                    if (newX - enemy.getCircle().getRadius() < 0 || newX + enemy.getCircle().getRadius() > 800) {
                        velocity[0] = -velocity[0]; // Inversion de la vitesse X
                    }
                    if (newY - enemy.getCircle().getRadius() < 0 || newY + enemy.getCircle().getRadius() > 600) {
                        velocity[1] = -velocity[1]; // Inversion de la vitesse Y
                    }

                    // Appliquer le mouvement
                    enemy.getCircle().setCenterX(enemy.getCircle().getCenterX() + velocity[0]);
                    enemy.getCircle().setCenterY(enemy.getCircle().getCenterY() + velocity[1]);

                    enemyVelocities.put(enemy, velocity); // Mettre à jour la vitesse
                }
            }
        };
        timer.start();
    }


}