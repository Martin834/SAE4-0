package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.*;
import com.example.sae4_project.QuadTree.Camera;
import com.example.sae4_project.QuadTree.Coordinate;
import com.example.sae4_project.QuadTree.Map;
import com.example.sae4_project.QuadTree.QuadTree;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AgarioController extends Controller {
    @FXML
    private Pane terrain;
    @FXML
    private Pane miniMap;
    @FXML
    private AnchorPane conteneurGlobal;


    private Player player;
    private Pellet touchedPellet;
    private ArrayList<Pellet> allPellets = new ArrayList<Pellet>();
    private double posX;
    private double posY;

    private Map map = Map.getInstance();
    private Camera cam = new Camera(new Coordinate(0, 0));

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




        map.getAllPellet(map.getQuadTree(), allPellets);

        for (Pellet elm : allPellets) {
            addCircle(elm.getCircle());
        }

        this.player = new CreatorPlayer().create();
        Circle circle = this.player.getCircle();
        addCircle(circle);


        cam.getCoordinate().XProperty().bind(Bindings.add(
                Bindings.multiply(-1,
                        Bindings.divide(conteneurGlobal.widthProperty(), 2)), circle.centerXProperty()));
        cam.getCoordinate().YProperty().bind(Bindings.add(
                Bindings.multiply(-1,
                        Bindings.divide(conteneurGlobal.heightProperty(), 2)), circle.centerYProperty()));
        terrain.translateXProperty().bind(Bindings.multiply(-1, cam.getCoordinate().XProperty()));
        terrain.translateYProperty().bind(Bindings.multiply(-1, cam.getCoordinate().YProperty()));


        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, handler);

        this.gameLoop();

        miniMap.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

/*
        double scaleX = miniMap.getPrefWidth() / terrain.getPrefWidth();
        double scaleY = miniMap.getPrefHeight() / terrain.getPrefHeight();

        Circle miniPlayer = new Circle(player.getCircle().getRadius() * scaleX, Color.RED);
        miniPlayer.centerXProperty().bind(player.getCircle().centerXProperty().multiply(scaleX));
        miniPlayer.centerYProperty().bind(player.getCircle().centerYProperty().multiply(scaleY));
        miniMap.getChildren().add(miniPlayer);
        for (Pellet pellet : allPellets) {
            Circle miniPellet = new Circle(pellet.getCircle().getRadius() * scaleX, Color.BLUE);
            miniPellet.setCenterX(pellet.getCircle().getCenterX() * scaleX);
            miniPellet.setCenterY(pellet.getCircle().getCenterY() * scaleY);
            miniMap.getChildren().add(miniPellet);
        }
        miniMap.widthProperty().addListener((obs, oldVal, newVal) -> {
            double newScaleX = miniMap.getPrefWidth() / newVal.doubleValue();
            System.out.println("Nouveau scaleX : " + newScaleX);
        });
        miniMap.heightProperty().addListener((obs, oldVal, newVal) -> {
            double newScaleY = miniMap.getPrefHeight() / newVal.doubleValue();
            System.out.println("Nouveau scaleY : " + newScaleY);
        });

        System.out.println("ScaleX: " + scaleX + ", ScaleY: " + scaleY);
*/




    }

    EventHandler handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            player.moveTowards(mouseEvent.getX(), mouseEvent.getY());

        }
    };

    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                player.move();


                touchedPellet = player.detectPellet(allPellets);
                if (touchedPellet != null) {
                    //System.out.println(touchedPellet.getIdentifier());
                    player.makeFatter(touchedPellet);
                    terrain.getChildren().remove(touchedPellet.getCircle());
                    allPellets.remove(touchedPellet);
                }
                touchedPellet = null;

            }
        };
        timer.start();
    }


}