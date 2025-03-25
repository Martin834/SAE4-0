package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.CreatorPellet;
import com.example.sae4_project.Entity.CreatorPlayer;
import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;
import com.example.sae4_project.QuadTree.Camera;
import com.example.sae4_project.QuadTree.Coordinate;
import com.example.sae4_project.QuadTree.Map;
import com.example.sae4_project.QuadTree.QuadTree;
import javafx.animation.AnimationTimer;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class AgarioController extends Controller {
    @FXML
    private Pane terrain;
    @FXML
    private Pane miniMap;
    @FXML
    private AnchorPane conteneurGlobal;

    private Player player;

    private double posX;
    private double posY;

    private Map map = Map.getInstance();
    private Camera cam = new Camera(new Coordinate(0,0));

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

        this.miniMap.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        for (int i=0; i<100 ; i++) {
            Pellet pellet = new CreatorPellet().create();
            Circle circlePellet = pellet.getCircle();
            addCircle(circlePellet);
        }

        this.player = new CreatorPlayer().create();
        Circle circle = this.player.getCircle();
        addCircle(circle);



        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, handler);

        this.gameLoop();
    }

    EventHandler handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            System.out.println(mouseEvent.getX() + ", " + mouseEvent.getY());
            posX = mouseEvent.getX();
            posY = mouseEvent.getY();

        }
    };

    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                player.moveTowards(posX, posY);
            }
        };
        timer.start();
    }

}