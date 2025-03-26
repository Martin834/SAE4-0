package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.*;
import com.example.sae4_project.QuadTree.Camera;
import com.example.sae4_project.QuadTree.Coordinate;
import com.example.sae4_project.QuadTree.Map;
import com.example.sae4_project.QuadTree.QuadTree;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.math.MathContext;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class AgarioController extends Controller {
    @FXML
    private Pane terrain;
    @FXML
    private Pane miniMap;
    @FXML
    private AnchorPane conteneurGlobal;


    private static Player player;
    private Pellet touchedPellet;
    private Pellet touchedByEnemy;
    private static ArrayList<Pellet> allPellets = new ArrayList<Pellet>();
    private static ArrayList<Enemy> allEnemy = new ArrayList<Enemy>();
    private double posX;
    private double posY;

    private Map map = Map.getInstance();
    private Camera cam = new Camera(new Coordinate(0,0));

    public static Player getPlayer() {
        return player;
    }

    public static List<Pellet> getPellets() {
        return allPellets;
    }

    @FXML
    private void addCircle(Circle circle) {

        this.terrain.getChildren().add(circle);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //map.generateEnemiesInQuadTree();


        this.terrain.prefHeightProperty().bind(conteneurGlobal.heightProperty());
        this.terrain.prefWidthProperty().bind(conteneurGlobal.widthProperty());
        this.terrain.setLayoutX(0);
        this.terrain.setLayoutY(0);

        this.miniMap.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        map.getAllPellet(map.getQuadTree(), allPellets);

        for(Pellet elm : allPellets){
            addCircle(elm.getCircle());
        }

        map.getAllEnemies(map.getQuadTree(), allEnemy);

        for(Enemy elm : allEnemy){
            addCircle(elm.getCircle());
            System.out.println(elm.getStrategy());
        }

        this.player = new CreatorPlayer().create();
        Circle circle = this.player.getCircle();
        addCircle(circle);

        cam.getCoordinate().XProperty().bind( Bindings.add(Bindings.multiply(-1,
                Bindings.divide( conteneurGlobal.widthProperty(), 2)) , circle.centerXProperty()));
        cam.getCoordinate().YProperty().bind(Bindings.add(Bindings.multiply(-1,
                Bindings.divide( conteneurGlobal.heightProperty(), 2)), circle.centerYProperty()));
        cam.zoomProperty().bind(Bindings.divide(5,
                Bindings.createDoubleBinding(()-> Math.sqrt(Math.sqrt(10*player.massProperty().get())), player.massProperty())));

        terrain.translateXProperty().bind(Bindings.multiply(-1,cam.getCoordinate().XProperty()));
        terrain.translateYProperty().bind(Bindings.multiply(-1,cam.getCoordinate().YProperty()));

        terrain.translateXProperty().bind(Bindings.multiply(terrain.scaleXProperty(), Bindings.multiply(-1,cam.getCoordinate().XProperty())));
        terrain.translateYProperty().bind(Bindings.multiply(terrain.scaleYProperty(),Bindings.multiply(-1,cam.getCoordinate().YProperty())));

        terrain.scaleXProperty().bind(cam.zoomProperty());
        terrain.scaleYProperty().bind(cam.zoomProperty());


        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, handlerMouseMoved);
        this.terrain.addEventHandler(ScrollEvent.SCROLL, handlerScrolled);
        this.gameLoop();
    }

    EventHandler handlerMouseMoved = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            player.moveTowards( mouseEvent.getX(), mouseEvent.getY(), player.calculateMaxSpeed());
        }
    };

    EventHandler handlerScrolled = new EventHandler<ScrollEvent>() {
        @Override
        public void handle(ScrollEvent scrollEvent) {
            if(scrollEvent.getDeltaY() > 0){
                System.out.println("haut");
            }else {
                System.out.println("bas");
            }

        }
    };

    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.move();

                // Gestion de la nourriture du joueur
                touchedPellet = player.detectPellet(allPellets);
                if (touchedPellet != null) {
                    player.makeFatter(touchedPellet);
                    terrain.getChildren().remove(touchedPellet.getCircle());
                    allPellets.remove(touchedPellet);
                }

                for (int i = 0; i < allEnemy.size(); i++) {
                    Enemy enemy = allEnemy.get(i);
                    enemy.executeStrategy(now);

                    // Gestion de la nourriture des ennemis
                    touchedByEnemy = enemy.detectPellet(allPellets);
                    if (touchedByEnemy != null) {
                        enemy.makeFatter(touchedByEnemy);
                        terrain.getChildren().remove(touchedByEnemy.getCircle());
                        allPellets.remove(touchedByEnemy);
                    }

                    enemy.move();

                    // Collision IA vs IA
                    for (int j = i + 1; j < allEnemy.size(); j++) {
                        Enemy otherEnemy = allEnemy.get(j);
                        if (enemy.isColliding(otherEnemy)) {
                            if (enemy.circle.getRadius() > otherEnemy.circle.getRadius()) {
                                enemy.makeFatter(otherEnemy); // Utilisation de la méthode générique
                                terrain.getChildren().remove(otherEnemy.getCircle());
                                allEnemy.remove(j);
                                j--; // Ajuste l'index après suppression
                            } else if (enemy.circle.getRadius() < otherEnemy.circle.getRadius()) {
                                otherEnemy.makeFatter(enemy); // Utilisation de la méthode générique
                                terrain.getChildren().remove(enemy.getCircle());
                                allEnemy.remove(i);
                                i--; // Ajuste l'index après suppression
                                break; // Sortir de la boucle pour éviter des erreurs
                            }
                        }
                    }

                    // Collision Joueur vs Ennemi
                    if (player.isColliding(enemy)) {
                        if (player.circle.getRadius() > enemy.circle.getRadius()) {
                            player.makeFatter(enemy); // Utilisation de la méthode générique
                            terrain.getChildren().remove(enemy.getCircle());
                            allEnemy.remove(i);
                            i--; // Ajuste l'index après suppression
                        } else {
                            System.out.println("Game Over ! Tu t'es fait manger.");
                            stop(); // Arrête le jeu
                        }
                    }
                }
            }
        };
        timer.start();
    }


    public void spawnPellets(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * terrain.getWidth();
            double y = random.nextDouble() * terrain.getHeight();
            Pellet newPellet = new Pellet();
            allPellets.add(newPellet);
            terrain.getChildren().add(newPellet.getCircle());
        }
    }
}