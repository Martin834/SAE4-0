package com.example.sae4_project.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AgarioApplication extends Application {

    private static Stage primaryStage;

    /**
     * Launches the application.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/sae4_project/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Agar.io - Menu");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the root of the page
     * @param fxml
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(AgarioApplication.class.getResource("/com/example/sae4_project/" + fxml + ".fxml"));
        Parent root = loader.load();
        primaryStage.getScene().setRoot(root);
    }

    public static void main(String[] args) {
        launch();
    }
}
