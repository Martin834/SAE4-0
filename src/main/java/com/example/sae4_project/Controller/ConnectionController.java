package com.example.sae4_project.Controller;

import com.example.sae4_project.Online.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionController extends Controller{
    @FXML
    private Button host;

    @FXML
    private Button join;

    @FXML
    private TextField ip;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
