package com.example.sae4_project.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class AgarioApplication extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sae4_project/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        //System.out.println(getClass().getResource("/com/example/sae4_project/img/logo.ico"));

        Image icon = new Image(String.valueOf(getClass().getResource("/com/example/sae4_project/img/logo.png")));
        stage.getIcons().add(icon);
        System.out.println("Icon URL: " + icon);

        stage.setTitle("Agar.io");

        stage.setScene(scene);
        stage.show();
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
