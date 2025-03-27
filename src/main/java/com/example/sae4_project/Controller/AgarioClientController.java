package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;
import com.example.sae4_project.Online.DataMap;
import com.example.sae4_project.Online.DataPlayer;
import com.example.sae4_project.Online.ServerMessageReceiver;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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

    ObjectOutputStream out;
    ServerMessageReceiver receiver;

    Player p;

    ArrayList<Pellet> pellets = new ArrayList<>();

    public AgarioClientController(Socket client, ServerMessageReceiver receiver) throws IOException {
        this.out = new ObjectOutputStream(client.getOutputStream());
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

        p = new Player();
        DataPlayer dataP = new DataPlayer(p);

        try {
            out.writeObject(dataP);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void gameLoop() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                DataMap data;
                synchronized(receiver.getLastDataMap()){
                    data = receiver.getLastDataMap();
                }

                for(Pellet pellet : pellets){
                    if(!data.recreatePellets().contains(pellet)){
                        terrain.getChildren().remove(pellet.getCircle());
                    }
                }

                for(Pellet pellet : data.recreatePellets()){
                    if(!pellets.contains(pellet)){
                        terrain.getChildren().add(pellet.getCircle());
                        pellets.add(pellet);
                    }
                }

            }
        };
    }
}
