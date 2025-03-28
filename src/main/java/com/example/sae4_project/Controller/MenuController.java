package com.example.sae4_project.Controller;

import com.example.sae4_project.Entity.CreatorPlayer;
import com.example.sae4_project.Entity.Player;
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
    private Button playLocalButton;

    @FXML
    private TextField pseudo;

    private Player player = new CreatorPlayer().create();



    public String getSpeudo(){
       // System.out.println("speudo " + pseudo.getText());

        return  pseudo.getText();

    }


    @FXML
    public void playLocal(ActionEvent event) throws IOException {
        //System.out.println("lancée");
        player.setName(pseudo.getText());
        System.out.println("MenuController: " + player.getName());
        switchScene(event, "/com/example/sae4_project/agario-view.fxml");
    }

    @FXML
    public void playOnline(ActionEvent event) throws IOException {
        // TODO: Ajouter la gestion de la connexion au serveur
        System.out.println("Mode en ligne non encore implémenté.");
    }

    @FXML
    public void exitGame(ActionEvent event) {
        System.exit(0);
    }

    public void switchScene(ActionEvent event, String fxmlFile) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

        AgarioController agarioController = new AgarioController();

        agarioController.setPlayer(this.player);
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
