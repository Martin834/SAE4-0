package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.*;
import com.example.sae4_project.QuadTree.Camera;
import com.example.sae4_project.QuadTree.Coordinate;
import com.example.sae4_project.QuadTree.Map;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    private ArrayList<Circle> listCirclesPlayer = new ArrayList<Circle>();
    private Player player;
    private Pellet touchedPellet;
    private ArrayList<Pellet> allPellets = new ArrayList<Pellet>();
    private double posX;
    private double posY;

    private Map map = Map.getInstance();
    private Camera cam = new Camera(new Coordinate(0,0));

    @FXML
    private void addCircle(Circle circle) {
        this.terrain.getChildren().add(circle);
    }

    @FXML
    private void removeCircle(Circle circle) {
        this.terrain.getChildren().remove(circle);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.terrain.prefHeightProperty().bind(conteneurGlobal.heightProperty());
        this.terrain.prefWidthProperty().bind(conteneurGlobal.widthProperty());
        this.terrain.setLayoutX(0);
        this.terrain.setLayoutY(0);
        this.terrain.setFocusTraversable(true);
        this.terrain.requestFocus();

        this.miniMap.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        map.getAllPellet(map.getQuadTree(), allPellets);

        for(Pellet elm : allPellets){
            addCircle(elm.getCircle());
        }

        this.player = new CreatorPlayer().create();
        Circle circle = player.getCirclesList().get(0);
        addCircle(circle);

        cam.getCoordinate().XProperty().bind( Bindings.add(Bindings.multiply(-1,
                Bindings.divide( conteneurGlobal.widthProperty(), 2)) , circle.centerXProperty()));
        cam.getCoordinate().YProperty().bind(Bindings.add(Bindings.multiply(-1,
                Bindings.divide( conteneurGlobal.heightProperty(), 2)), circle.centerYProperty()));
        cam.zoomProperty().bind(Bindings.divide(7,
                Bindings.createDoubleBinding(()-> Math.sqrt(Math.sqrt(Math.sqrt(6*player.massProperty().get()))), player.massProperty())));

        terrain.translateXProperty().bind(Bindings.multiply(-1,cam.getCoordinate().XProperty()));
        terrain.translateYProperty().bind(Bindings.multiply(-1,cam.getCoordinate().YProperty()));

        terrain.translateXProperty().bind(Bindings.multiply(terrain.scaleXProperty(), Bindings.multiply(-1,cam.getCoordinate().XProperty())));
        terrain.translateYProperty().bind(Bindings.multiply(terrain.scaleYProperty(),Bindings.multiply(-1,cam.getCoordinate().YProperty())));

        terrain.scaleXProperty().bind(cam.zoomProperty());
        terrain.scaleYProperty().bind(cam.zoomProperty());

        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, handlerMouseMoved);
        this.terrain.addEventHandler(KeyEvent.KEY_PRESSED, handlerSpace);

        this.gameLoop();
    }

    EventHandler<MouseEvent> handlerMouseMoved = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            player.moveTowards(mouseEvent.getX(), mouseEvent.getY(), player.calculateMaxSpeed());
        }
    };

    EventHandler<KeyEvent> handlerSpace = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                player.divideItself();
                for (Circle circle : player.getCirclesList()) {
                    removeCircle(circle);
                }
                for (Circle circle : player.getCirclesList()) {
                    addCircle(circle);
                }
            }
        }
    };

    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                player.move();

                touchedPellet = player.detectPellet(allPellets);
                if (touchedPellet != null) {
                    for (Circle circle : player.getCirclesList()) {
                        if (circle.getBoundsInLocal().intersects(touchedPellet.getCircle().getBoundsInLocal())) {
                            player.makeFatter(touchedPellet, circle);
                            terrain.getChildren().remove(touchedPellet.getCircle());
                            allPellets.remove(touchedPellet);
                            break;
                        }
                    }
                }
                touchedPellet = null;
            }
        };
        timer.start();
    }

}
