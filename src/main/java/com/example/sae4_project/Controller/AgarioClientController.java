package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.CreatorPlayer;
import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;
import com.example.sae4_project.Online.DataMap;
import com.example.sae4_project.Online.DataMessage;
import com.example.sae4_project.Online.DataPlayer;
import com.example.sae4_project.Online.ServerMessageReceiver;
import com.example.sae4_project.QuadTree.Camera;
import com.example.sae4_project.QuadTree.Coordinate;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.*;

public class AgarioClientController extends Controller {

    @FXML
    private Pane terrain;
    @FXML
    private Pane miniMap;
    @FXML
    private VBox leaderboard;
    @FXML
    private AnchorPane conteneurGlobal;

    private Camera cam = new Camera(new Coordinate(0, 0));

    ObjectOutputStream out;
    ServerMessageReceiver receiver;

    Player player;

    ArrayList<Pellet> pellets = new ArrayList<>();
    ArrayList<Player> players = new ArrayList<>();
    private Circle circleCam;

    public AgarioClientController(Socket client, ServerMessageReceiver receiver) throws IOException {
        this.out = new ObjectOutputStream(client.getOutputStream());
        this.receiver = receiver;
    }

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

        DataMap init;
        synchronized(receiver.getLastDataMap()){
            init = receiver.getLastDataMap();
        }

        for(Pellet pellet : init.recreatePellets()){
            this.terrain.getChildren().add(pellet.getCircle());
            pellets.add(pellet);
        }

        this.player = new CreatorPlayer().create();
        circleCam = player.getCirclesList().get(0);
        addCircle(circleCam);
        this.players.add(player);


        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, handlerMouseMoved);
        this.terrain.addEventHandler(KeyEvent.KEY_PRESSED, handlerSpace);

        cam.getCoordinate().XProperty().bind( Bindings.add(Bindings.multiply(-1,
                Bindings.divide( conteneurGlobal.widthProperty(), 2)) , circleCam.centerXProperty()));
        cam.getCoordinate().YProperty().bind(Bindings.add(Bindings.multiply(-1,
                Bindings.divide( conteneurGlobal.heightProperty(), 2)), circleCam.centerYProperty()));
        cam.zoomProperty().bind(Bindings.divide(7,
                Bindings.createDoubleBinding(()-> Math.sqrt(Math.sqrt(Math.sqrt(6*player.massProperty().get()))), player.massProperty())));

        terrain.translateXProperty().bind(Bindings.multiply(-1,cam.getCoordinate().XProperty()));
        terrain.translateYProperty().bind(Bindings.multiply(-1,cam.getCoordinate().YProperty()));

        terrain.translateXProperty().bind(Bindings.multiply(terrain.scaleXProperty(), Bindings.multiply(-1,cam.getCoordinate().XProperty())));
        terrain.translateYProperty().bind(Bindings.multiply(terrain.scaleYProperty(),Bindings.multiply(-1,cam.getCoordinate().YProperty())));

        terrain.scaleXProperty().bind(cam.zoomProperty());
        terrain.scaleYProperty().bind(cam.zoomProperty());

        DataMessage data = new DataMessage(player.getIdentifier(),dx, dy, false);

        try {
            out.writeObject(data);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.gameLoop();

    }

    private double dx;
    private double dy;
    EventHandler<MouseEvent> handlerMouseMoved = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            dx = mouseEvent.getX();
            dy = mouseEvent.getY();
        }
    };

    EventHandler<KeyEvent> handlerSpace = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.getCode() == KeyCode.SPACE) {

            }
        }
    };

    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                player.move();

                DataMap data;
                synchronized(receiver.getLastDataMap()){
                    data = receiver.getLastDataMap();
                }

                for(Player p : data.recreatePlayers()){
                    System.out.println(p.getCirclesList().get(0).getCenterX());
                    circleCam.setCenterX(p.getCirclesList().get(0).getCenterX());
                    circleCam.setCenterY(p.getCirclesList().get(0).getCenterY());
                }


                DataMessage msg = new DataMessage(player.getIdentifier(),dx, dy, false);

                try {
                    out.writeObject(msg);
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }



            }
        };
        timer.start();
    }
}
