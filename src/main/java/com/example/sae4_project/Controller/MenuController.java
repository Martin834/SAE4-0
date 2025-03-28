package com.example.sae4_project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends Controller{
    @FXML
    private ImageView homeImage;

    @FXML
    private void playLocal(ActionEvent event) throws IOException {
        switchScene(event, "/com/example/sae4_project/agario-view.fxml");
    }

    @FXML
    private void playOnline(ActionEvent event) throws IOException {
        // TODO: Ajouter la gestion de la connexion au serveur
        System.out.println("Mode en ligne non encore implémenté.");
    }

    @FXML
    private void exitGame(ActionEvent event) {
        System.exit(0);
    }

    private void switchScene(ActionEvent event, String fxmlFile) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeImage.setImage(new Image(String.valueOf(getClass().getResource("/com/example/sae4_project/img/welcome.png"))));

    }
}
