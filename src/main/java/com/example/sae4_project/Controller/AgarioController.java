package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.*;
import com.example.sae4_project.QuadTree.Camera;
import com.example.sae4_project.QuadTree.Coordinate;
import com.example.sae4_project.QuadTree.Map;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import java.util.ResourceBundle;

public class AgarioController extends Controller {
    @FXML
    private Pane terrain;
    @FXML
    private Pane miniMap;
    @FXML
    private AnchorPane globalPane;


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
        // Binding the terrain's size and the globalPane's size
        this.terrain.prefHeightProperty().bind(globalPane.heightProperty());
        this.terrain.prefWidthProperty().bind(globalPane.widthProperty());
        this.terrain.setLayoutX(0);
        this.terrain.setLayoutY(0);

        // Load pellets and display themm on the map
        map.getAllPellet(map.getQuadTree(), allPellets);
        for (Pellet elm : allPellets) {
            addCircle(elm.getCircle());
        }

        // Add the player on the map
        this.player = new CreatorPlayer().create();
        Circle circle = this.player.getCircle();
        addCircle(circle);

        cam.getCoordinate().XProperty().bind( Bindings.add(Bindings.multiply(-1,
                Bindings.divide( globalPane.widthProperty(), 2)) , circle.centerXProperty()));
        cam.getCoordinate().YProperty().bind(Bindings.add(Bindings.multiply(-1,
                Bindings.divide( globalPane.heightProperty(), 2)), circle.centerYProperty()));
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

        // Setup of the minimap
        miniMap.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Creation of the player on the minimap
        Circle miniPlayer = new Circle(player.getCircle().getRadius(), Color.RED);
        miniMap.getChildren().add(miniPlayer);


        //Listen to the player to update the minimap
        updateMiniMapScale(miniPlayer);
        player.getCircle().centerXProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale(miniPlayer));
        player.getCircle().centerYProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale(miniPlayer));

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
            if (scrollEvent.getDeltaY() > 0) {
                System.out.println("haut");
            } else {
                System.out.println("bas");
            }
        }
    };

    /**
     * Update the minimap
     */
    private void updateMiniMapScale(Circle miniPlayer) {
        System.out.println("1:"+miniPlayer.getRadius());
        double scale = miniMap.getWidth() / Map.size;


        // Update the player on the minimap
        miniPlayer.setRadius(player.getCircle().getRadius() * scale);
        miniPlayer.centerXProperty().bind(player.getCircle().centerXProperty().multiply(scale));
        miniPlayer.centerYProperty().bind(player.getCircle().centerYProperty().multiply(scale));


    }


    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                player.move();

                touchedPellet = player.detectPellet(allPellets);
                if (touchedPellet != null) {
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