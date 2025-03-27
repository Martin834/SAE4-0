package com.example.sae4_project.Application;

import com.example.sae4_project.Controller.AgarioController;
import com.example.sae4_project.Controller.ConnectionController;
import com.example.sae4_project.Controller.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.stage.WindowEvent;


import java.io.IOException;
import java.util.ArrayList;

public class AgarioApplication extends Application {


        private static Scene scene;
        public static ArrayList<Thread> threads = new ArrayList<>();
        private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sae4_project/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Agar.io - Menu");

        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((WindowEvent event) ->
        {
            for(Thread th : threads){
                th.interrupt();
            }
        });
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(AgarioApplication.class.getResource("/com/example/sae4_project/" + fxml + ".fxml"));
        Parent root = loader.load();
        primaryStage.getScene().setRoot(root);
    }

    public static void main(String[] args) {
        launch();
    }

}

