package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.*;
import com.example.sae4_project.QuadTree.Camera;
import com.example.sae4_project.QuadTree.Coordinate;
import com.example.sae4_project.QuadTree.Map;
import com.example.sae4_project.QuadTree.QuadTree;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.math.MathContext;
import java.net.URL;
import java.util.*;
import java.util.random.RandomGenerator;

public class AgarioController extends Controller {
    private static final int MAX_ENEMIES = 20;
    private static final int MAX_PELLETS = 1000;
    @FXML
    private Pane terrain;
    @FXML
    private Pane miniMap;
    @FXML
    private Label scoreLabel;
    @FXML
    private AnchorPane conteneurGlobal;


    private static Player player;
    private Pellet touchedPellet;
    private SpecialPellets touchedSpecialPellet;
    private Pellet touchedByEnemy;
    private static ArrayList<SpecialPellets> allSpecialPellets = new ArrayList<SpecialPellets>();
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
    public static List<SpecialPellets> getSpecialPellets() {
        return allSpecialPellets;
    }

    @FXML
    private void addCircle(Circle circle) {

        this.terrain.getChildren().add(circle);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.terrain.prefHeightProperty().bind(conteneurGlobal.heightProperty());
        this.terrain.prefWidthProperty().bind(conteneurGlobal.widthProperty());
        this.terrain.setLayoutX(0);
        this.terrain.setLayoutY(0);
        /*this.conteneurGlobal.setBorder(new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                new BorderWidths(2))));*/
        //scoreLabel.textProperty().bind(new SimpleStringProperty());
        //Bindings.stringValueAt(pla);

        this.miniMap.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        map.getAllPellet(map.getQuadTree(), allPellets);
        map.getAllSpecialPellet(map.getQuadTree(),allSpecialPellets);
        map.getAllEnemies(map.getQuadTree(), allEnemy);

        for (SpecialPellets elm : allSpecialPellets){
            addCircle(elm.getCircle());
        }

        for(Pellet elm : allPellets){
            addCircle(elm.getCircle());
        }

        for(Enemy elm : allEnemy){
            addCircle(elm.getCircle());
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
                System.out.println("nb Specialpellets : " + allSpecialPellets.size());
                System.out.println("nb pellets : " + allPellets.size());
                Random random = new Random();
                int enemysize = allEnemy.size();
                if (enemysize < MAX_ENEMIES){
                    spawnEnemies();
                }

                int pelletsNB = allPellets.size()+allSpecialPellets.size();
                if (pelletsNB < MAX_PELLETS){
                    spawnPellets();
                }

                touchedPellet = player.detectPellet(allPellets);
                touchedSpecialPellet = player.detectSpecialPellet(allSpecialPellets);

                if (touchedSpecialPellet != null) {
                    player.makeFatter(touchedSpecialPellet);
                    player.speed = player.calculateMaxSpeed();
                    touchedSpecialPellet.applyEffect(player);
                    terrain.getChildren().remove(touchedSpecialPellet.getCircle());
                    allSpecialPellets.remove(touchedSpecialPellet);
                }
                if(touchedPellet != null) {
                    player.makeFatter(touchedPellet);
                    player.speed = player.calculateMaxSpeed();
                    terrain.getChildren().remove(touchedPellet.getCircle());
                    allPellets.remove(touchedPellet);
                }
                for (int i = 0; i < allEnemy.size(); i++) {
                    Enemy enemy = allEnemy.get(i);
                    enemy.executeStrategy(now);

                    touchedByEnemy = enemy.detectPellet(allPellets);
                    touchedSpecialPellet = enemy.detectSpecialPellet(allSpecialPellets);
                    if (touchedByEnemy != null) {
                        enemy.makeFatter(touchedByEnemy);
                        enemy.speed = enemy.calculateMaxSpeed();

                        terrain.getChildren().remove(touchedByEnemy.getCircle());
                        allPellets.remove(touchedByEnemy);
                    }
                    if (touchedSpecialPellet != null) {
                        enemy.makeFatter(touchedByEnemy);
                        enemy.speed = enemy.calculateMaxSpeed();

                        touchedSpecialPellet.applyEffect(enemy);
                        terrain.getChildren().remove(touchedSpecialPellet.getCircle());
                        allPellets.remove(touchedSpecialPellet);
                    }

                    enemy.move();

                    for (int j = i + 1; j < allEnemy.size(); j++) {
                        Enemy otherEnemy = allEnemy.get(j);
                        if (enemy.isColliding(otherEnemy)) {
                            if (enemy.circle.getRadius() > otherEnemy.circle.getRadius()) {
                                enemy.makeFatter(otherEnemy);
                                enemy.speed = enemy.calculateMaxSpeed();

                                terrain.getChildren().remove(otherEnemy.getCircle());
                                allEnemy.remove(j);
                                j--;
                            } else if (enemy.circle.getRadius() < otherEnemy.circle.getRadius()) {
                                otherEnemy.makeFatter(enemy);
                                otherEnemy.speed = enemy.calculateMaxSpeed();

                                terrain.getChildren().remove(enemy.getCircle());
                                allEnemy.remove(i);
                                i--;
                                break;
                            }
                        }
                    }

                    if (player.isColliding(enemy)) {
                        double playerMass = player.getMass();
                        double enemyMass = enemy.getMass();

                        if (playerMass >= enemyMass * 1.33) {
                            player.makeFatter(enemy);
                            player.speed = player.calculateMaxSpeed();

                            terrain.getChildren().remove(enemy.getCircle());
                            allEnemy.remove(i);
                            i--;
                        }
                        else if (enemyMass >= playerMass * 1.33) {
                            System.out.println("Game Over ! Tu t'es fait manger.");
                            stop();
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Game Over");
                                alert.setHeaderText("Tu t'es fait manger !");
                                alert.setContentText("Dommage, tu as perdu le jeu.");

                                alert.showAndWait().ifPresent(response -> {
                                    if (response == ButtonType.OK) {
                                        System.exit(0);
                                    }
                                });
                            });
                        }
                    }
                }
            }
        };
        timer.start();
    }
    public void spawnEnemies() {
        Random random = new Random();
        Enemy e = new CreatorEnemy().create(random.nextDouble(0,Map.size),random.nextDouble(0,Map.size));
        allEnemy.add(e);
        addCircle(e.getCircle());
    }
    public void spawnPellets() {
        Random random = new Random();
        System.out.println("-------------------------------------------");
        if (random.nextDouble() < 0.2) {
            SpecialPellets sp = new CreatorSpecialPellets().create(random.nextDouble(0, Map.size), random.nextDouble(0, Map.size));
            allSpecialPellets.add(sp);
            addCircle(sp.getCircle());
        } else {
            Pellet p = new CreatorPellet().create(random.nextDouble(0, Map.size), random.nextDouble(0, Map.size));
            allPellets.add(p);
            addCircle(p.getCircle());
        }
    }

}