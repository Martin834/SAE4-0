package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.CreatorPellet;
import com.example.sae4_project.Entity.CreatorPlayer;
import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class AgarioController implements Initializable {
    @FXML
    private Pane terrain;

    private Player player;

    @FXML
    private void addCircle(Circle circle) {
        this.terrain.getChildren().add(circle);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int i=0; i<100 ; i++) {
            Pellet pellet = new CreatorPellet().create();
            Circle circlePellet = pellet.getCircle();
            addCircle(circlePellet);
        }

        this.player = new CreatorPlayer().create();
        Circle circle = this.player.getCircle();
        addCircle(circle);



        this.terrain.addEventHandler(MouseEvent.MOUSE_MOVED, handler);
    }

    EventHandler handler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            System.out.println(mouseEvent.getX() + ", " + mouseEvent.getY());
            player.moveTowards(mouseEvent.getX(), mouseEvent.getY());
            //player.getCircle().setCenterX(player.getPosX());
            //player.getCircle().setCenterY(player.getPosY());
        }
    };
}