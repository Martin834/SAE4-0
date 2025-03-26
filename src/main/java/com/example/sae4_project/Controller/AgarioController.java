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
        // Lier la taille du terrain à la taille du conteneur global
        this.terrain.prefHeightProperty().bind(conteneurGlobal.heightProperty());
        this.terrain.prefWidthProperty().bind(conteneurGlobal.widthProperty());
        this.terrain.setLayoutX(0);
        this.terrain.setLayoutY(0);

        // Charger les pellets et les afficher sur la carte principale
        map.getAllPellet(map.getQuadTree(), allPellets);
        for (Pellet elm : allPellets) {
            addCircle(elm.getCircle());
        }

        // Création du joueur et ajout sur la carte
        this.player = new CreatorPlayer().create();
        Circle circle = this.player.getCircle();
        addCircle(circle);

        // Liaison de la caméra avec le joueur
        cam.getCoordinate().XProperty().bind(Bindings.add(
                Bindings.multiply(-1, Bindings.divide(conteneurGlobal.widthProperty(), 2)),
                circle.centerXProperty()));
        cam.getCoordinate().YProperty().bind(Bindings.add(
                Bindings.multiply(-1, Bindings.divide(conteneurGlobal.heightProperty(), 2)),
                circle.centerYProperty()));

        terrain.translateXProperty().bind(Bindings.multiply(-1, cam.getCoordinate().XProperty()));
        terrain.translateYProperty().bind(Bindings.multiply(-1, cam.getCoordinate().YProperty()));

        // Gestion du mouvement du joueur
        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, handler);

        // Lancer la boucle de jeu
        this.gameLoop();

        // Configuration de la minimap
        miniMap.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Création du joueur sur la minimap
        miniPlayer = new Circle(player.getCircle().getRadius(), Color.RED);
        miniMap.getChildren().add(miniPlayer);

        // Ajouter les pellets sur la minimap
        for (Pellet pellet : allPellets) {
            Circle miniPellet = new Circle(pellet.getCircle().getRadius(), Color.BLUE);
            miniMap.getChildren().add(miniPellet);
        }

        // Mettre à jour les échelles et les positions des objets sur la minimap
        updateMiniMapScale();

        // Écouteurs pour mettre à jour les dimensions de la minimap dynamiquement
        terrain.widthProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale());
        terrain.heightProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale());
        miniMap.widthProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale());
        miniMap.heightProperty().addListener((obs, oldVal, newVal) -> updateMiniMapScale());
    }

    /**
     * Met à jour l'échelle et la position des objets sur la minimap
     */
    private void updateMiniMapScale() {
        double scaleX = miniMap.getWidth() / terrain.getWidth();
        double scaleY = miniMap.getHeight() / terrain.getHeight();

        // Mise à jour du joueur sur la minimap
        miniPlayer.setRadius(player.getCircle().getRadius() * scaleX);
        miniPlayer.centerXProperty().bind(player.getCircle().centerXProperty().multiply(scaleX));
        miniPlayer.centerYProperty().bind(player.getCircle().centerYProperty().multiply(scaleY));

        // Mise à jour des pellets sur la minimap
        int index = 1; // Commence à 1 car le miniPlayer est à l'index 0
        for (Pellet pellet : allPellets) {
            if (index < miniMap.getChildren().size()) {
                Circle miniPellet = (Circle) miniMap.getChildren().get(index);
                miniPellet.setRadius(pellet.getCircle().getRadius() * scaleX);
                miniPellet.centerXProperty().bind(pellet.getCircle().centerXProperty().multiply(scaleX));
                miniPellet.centerYProperty().bind(pellet.getCircle().centerYProperty().multiply(scaleY));
            }
            index++;
        }
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