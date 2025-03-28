package com.example.sae4_project.Application;

import com.example.sae4_project.Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

public class AgarioApplication extends Application {
        private static Scene scene;
        public static ArrayList<Thread> threads = new ArrayList<>();
        public static Stage primaryStage;

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
        Image icon = new Image(String.valueOf(getClass().getResource("/com/example/sae4_project/img/logo.png")));
        stage.getIcons().add(icon);
        System.out.println("Icon URL: " + icon);
        stage.setTitle("Agar.io");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((WindowEvent event) ->
        {
            for(Thread th : threads){
                th.interrupt();
            }
        });
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

    public static Parent loadFXML(String fxml, Controller controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AgarioApplication.class.getResource("/com/example/sae4_project/" + fxml + ".fxml"));
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }

    public static void setRoot(String fxml, Controller controller) throws IOException {
        scene.setRoot(loadFXML(fxml, controller));
    }

    public static void main(String[] args) {
        launch();
    }

}

