package com.example.sae4_project.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    public Button playLocalButton;

    @FXML
    public TextField pseudo;

    @FXML
    private void playLocalButton(){

    }

    public void saveSpeudo(){
        System.out.println("speudo " + pseudo.getText());
    }


    @FXML
    private void playLocal(ActionEvent event) throws IOException {
        System.out.println("lancée");
        saveSpeudo();
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
        // Récupérer le Stage à partir du bouton qui a déclenché l'événement
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
