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
    private static List<Pellet> pellets = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private Map<Enemy, double[]> enemyVelocities = new HashMap<>();


    private static Player player;

    private double posX;
    private double posY;

    public static Player getPlayer() {
        return player;
    }

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
            enemy.getCircle().setCenterX(r.nextDouble() * 800);
            enemy.getCircle().setCenterY(r.nextDouble() * 600);
            enemies.add(enemy);
            addCircle(enemy.getCircle());
        }

        // Création des pastilles
        for (int i = 0; i < 100; i++) {
            Pellet pellet = new CreatorPellet().create();
            addCircle(pellet.getCircle());
            pellets.add(pellet);
        }

        // Création du joueur
        this.player = new CreatorPlayer().create();
        addCircle(this.player.getCircle());

        // Capture des mouvements de la souris
        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, mouseEvent -> {
            posX = mouseEvent.getX();
            posY = mouseEvent.getY();
        });

        // Lancement de la boucle du jeu
        this.gameLoop();
    }

    public static List<Pellet> getPellets(){ return pellets; }


    EventHandler handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            posX = mouseEvent.getX();
            posY = mouseEvent.getY();

        }
    };

    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdateTime = 0; // Stocke le dernier moment où la mise à jour a été faite
            private final long updateInterval = 33_000_000L; // 33 ms en nanosecondes

            @Override
            public void handle(long now) {
                if (now - lastUpdateTime < updateInterval) {
                    return; // Attendre 33ms avant la prochaine mise à jour
                }
                lastUpdateTime = now;

                // Déplacement du joueur vers la souris
                player.moveTowards(posX, posY);

                // Exécute la stratégie de chaque ennemi toutes les 33ms
                for (Enemy enemy : enemies) {
                    enemy.executeStrategy(now);
                }
            }
        };
        timer.start();
    }




}