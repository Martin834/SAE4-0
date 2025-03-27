package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.*;
import com.example.sae4_project.QuadTree.Camera;
import com.example.sae4_project.QuadTree.Coordinate;
import com.example.sae4_project.QuadTree.Map;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AgarioController extends Controller {
    @FXML
    private Pane terrain;
    @FXML
    private Pane miniMap;
    @FXML
    private VBox leaderboard;
    @FXML
    private AnchorPane conteneurGlobal;

    private ArrayList<Circle> listCirclesPlayer = new ArrayList<Circle>();
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

        // Load pellets and display themm on the map
        map.getAllPellet(map.getQuadTree(), allPellets);
        for (Pellet elm : allPellets) {
            addCircle(elm.getCircle());
        }

        // Add the player on the map
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

        // Setup of the minimap
        miniMap.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Creation of the player on the minimap
        Circle miniPlayer = new Circle(player.getCirclesList().get(0).getRadius(), Color.RED);
        miniMap.getChildren().add(miniPlayer);

        //Listen to the player to update the minimap
        for (Circle circle1 : player.getCirclesList()) {
            circle1.centerXProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale(miniPlayer));
            circle1.centerYProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale(miniPlayer));
        }

        leaderboard.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        leaderboard.setSpacing(10);
        leaderboard.setPadding(new Insets(10, 20, 10, 10));  // (haut, droite, bas, gauche)


        Integer [] nombre = {1,2,3,4,5,6,7,8,9,10};
        Label [] noms = {new Label("joli"), new Label("sobriquet"), new Label("s'éclipse"), new Label("parole"),
                new Label("de"), new Label("moine"), new Label("dérolle"), new Label("dodécacophonique"),
                new Label("rythmique"), new Label("globule10")};

        for (int i = 0; i < 10 ; i++) {
            Label label = new Label(nombre[i].toString() + ". " + noms[i].getText());
            label.setPrefSize(200.0, 200.0);
            leaderboard.getChildren().add(label);
        }

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

    /**
     * Update the minimap
     */
    private void updateMiniMapScale(Circle miniPlayer) {
        System.out.println("1:"+miniPlayer.getRadius());
        double scale = miniMap.getWidth() / Map.size;


        // Update the player on the minimap
        for (Circle circle : player.getCirclesList()) {
            miniPlayer.setRadius(circle.getRadius() * scale);
            miniPlayer.centerXProperty().bind(circle.centerXProperty().multiply(scale));
            miniPlayer.centerYProperty().bind(circle.centerYProperty().multiply(scale));
        }



    }

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

