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

    @Override
    public void start(Stage stage) throws IOException {
        Controller controller = new AgarioController();
        scene = new Scene(loadFXML("agario-view", controller), 800, 600 );
        stage.setTitle("agar.io");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest((WindowEvent event) ->
        {
            for(Thread th : threads){
                th.interrupt();
            }
        });
    }

    public static void setRoot(String fxml, Controller controller) throws IOException {
        scene.setRoot(loadFXML(fxml, controller));
    }

    private static Parent loadFXML(String fxml, Controller controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AgarioApplication.class.getResource("/com/example/sae4_project/"+fxml + ".fxml"));
        fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }


}