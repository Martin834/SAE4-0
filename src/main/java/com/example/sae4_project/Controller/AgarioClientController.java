package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Online.DataMap;
import com.example.sae4_project.Online.ServerMessageReceiver;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AgarioClientController extends Controller {

    @FXML
    private Pane terrain;
    @FXML
    private Pane miniMap;
    @FXML
    private VBox leaderboard;
    @FXML
    private AnchorPane conteneurGlobal;

    Socket client;
    ServerMessageReceiver receiver;

    ArrayList<Pellet> pellets = new ArrayList<>();

    public AgarioClientController(Socket client, ServerMessageReceiver receiver) {
        this.client = client;
        this.receiver = receiver;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataMap init;
        synchronized(receiver.getLastDataMap()){
            init = receiver.getLastDataMap();
        }

        for(Pellet pellet : init.recreatePellets()){
            this.terrain.getChildren().add(pellet.getCircle());
            pellets.add(pellet);
        }

    }

    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

            }
        };
    }
}
