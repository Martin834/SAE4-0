package com.example.sae4_project.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {


    @FXML
    private Pane gameMap;
    @FXML
    private Pane miniMap;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.gameMap.heightProperty().bind();
    }
}
