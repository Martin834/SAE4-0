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

        ArrayList<Entity> liste = new ArrayList<>();
        map.getAllEntities(map.getQuadTree(), liste);

        for(Entity elm : liste){
            Pellet pellet = (Pellet) elm;
            addCircle(pellet.getCircle());
        }

        this.player = new CreatorPlayer().create();
        Circle circle = this.player.getCircle();
        addCircle(circle);

        System.out.println(conteneurGlobal.widthProperty().doubleValue());

        cam.getCoordinate().XProperty().bind( Bindings.add(
                Bindings.multiply(-1,
                        Bindings.divide( conteneurGlobal.widthProperty(), 2)) , circle.centerXProperty())); //circle.centerXProperty()));
        cam.getCoordinate().YProperty().bind(Bindings.add(
                Bindings.multiply(-1,
                        Bindings.divide( conteneurGlobal.heightProperty(), 2)), circle.centerYProperty())); // circle.centerYProperty()));
        terrain.translateXProperty().bind(Bindings.multiply(-1,cam.getCoordinate().XProperty()));
        terrain.translateYProperty().bind(Bindings.multiply(-1,cam.getCoordinate().YProperty()));

        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, handler);

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
                System.out.println(conteneurGlobal.widthProperty().doubleValue());
                player.moveTowards(posX, posY);

            }
        };
        timer.start();
    }

}