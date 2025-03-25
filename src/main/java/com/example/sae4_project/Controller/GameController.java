package com.example.sae4_project.Controller;

import com.example.sae4_project.QuadTree.Camera;
import com.example.sae4_project.QuadTree.Coordinate;
import com.example.sae4_project.QuadTree.Map;
import com.example.sae4_project.QuadTree.QuadTree;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends Controller {


    @FXML
    private Pane terrain;
    @FXML
    private Pane miniMap;
    @FXML
    private AnchorPane conteneurGlobal;

    private Map map = Map.getInstance();
    private Camera cam = new Camera(new Coordinate(0,0));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.terrain.prefHeightProperty().bind(conteneurGlobal.heightProperty());
        this.terrain.prefWidthProperty().bind(conteneurGlobal.widthProperty());
        this.terrain.setLayoutX(0);
        this.terrain.setLayoutY(0);

        this.terrain.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        this.miniMap.setLayoutX(conteneurGlobal.getWidth());
        this.miniMap.setLayoutY(conteneurGlobal.getHeight());
        this.miniMap.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        QuadTree zone = this.map.findQuadTree(map.getQuadTree(), new Coordinate(834,834));
        System.out.println(zone.getBoundry().getCoordinate().getX());
        System.out.println(zone.getBoundry().getCoordinate().getY());
    }

}
