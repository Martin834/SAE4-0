package com.example.sae4_project.Controller;

import com.example.sae4_project.Application.AgarioApplication;
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

    /**
     * Sets the scene to the main agario view when the local play button is pressed.
     * @param event
     * @throws IOException
     */
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

    /**
     * Sets the scene to the online menu view when the online button is pressed.
     * @param event
     * @throws IOException
     */
    @FXML
    public void playOnline(ActionEvent event) throws IOException {
        AgarioApplication.setRoot("connection");
    }

    /**
     * Exits the application when the exit button is pressed.
     * @param event
     */
    @FXML
    public void exitGame(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Switches the application's scene according to the fxmlFile parameter.
     * @param event
     * @param fxmlFile
     * @throws IOException
     */
    private void switchScene(ActionEvent event, String fxmlFile) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        AgarioController agarioController = new AgarioController();
        agarioController.setPlayer(this.player);
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
