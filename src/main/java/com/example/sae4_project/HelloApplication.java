package com.example.sae4_project;

import com.example.sae4_project.Controller.Controller;
import com.example.sae4_project.Controller.GameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("game", new GameController()), 800, 600 );
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml, Controller controller) throws IOException {
        scene.setRoot(loadFXML(fxml, controller));
    }

    private static Parent loadFXML(String fxml, Controller controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}