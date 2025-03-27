package com.example.sae4_project.Controller;

import com.example.sae4_project.Application.AgarioApplication;
import com.example.sae4_project.Online.OnlineState;
import com.example.sae4_project.Online.Server;
import com.example.sae4_project.Online.ServerManager;
import com.example.sae4_project.Online.ServerMessageReceiver;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ConnectionController extends Controller{

    @FXML
    private GridPane grid;

    @FXML
    private Button host;
    @FXML
    private Button join;
    @FXML
    private Button disconnect;
    @FXML
    private Button play;

    @FXML
    private TextField ip;
    @FXML
    private TextField port;

    Server server;
    Socket client;


    OnlineState onlineState;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.host.setOnAction(toHost);
        this.disconnect.setOnAction(toDisconnect);
        this.join.setOnAction(toJoin);
        this.play.setOnAction(toPlay);


        this.ip.disableProperty().bind(join.disableProperty());
        this.port.disableProperty().bind(join.disableProperty());

        onlineState = OnlineState.DISCONNECTED;
        host.setDisable(false);
        join.setDisable(false);
        disconnect.setDisable(true);
        play.setDisable(true);

    }

    EventHandler toHost = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                server = new Server();
                server.start();
                changeState(OnlineState.HOST);
            } catch (Exception e) {
                changeState(OnlineState.ERROR);
            }

        }
    };
    EventHandler toJoin = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                String hostName = ip.getText();
                int hostPort = Integer.parseInt(port.getText());
                client = new Socket(hostName, hostPort);

                ServerMessageReceiver receiver = new ServerMessageReceiver(client);
                receiver.start();

                changeState(OnlineState.GUEST);

            } catch (Exception e) {
                changeState(OnlineState.ERROR);
            }

        }
    };
    EventHandler toDisconnect = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                if(server != null){
                    server.disconnect();
                    server = null;
                }
                if(client != null){
                    client.close();
                    client = null;
                }


                changeState(OnlineState.DISCONNECTED);
            } catch (Exception e) {
                changeState(OnlineState.ERROR);
            }

        }
    };
    EventHandler toPlay = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {


        }
    };

    public void changeState(OnlineState newState) {
        Alert alert;
        switch(newState){
            case HOST :
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Connection information : ");
                alert.setHeaderText("Succesful connection");
                alert.setContentText("You are now hosting a game : \n IP : " + server.getServerManager().getSocket().getInetAddress() +
                        "\n Port : " + server.getServerManager().getSocket().getLocalPort());
                alert.showAndWait();

                host.setDisable(true);
                join.setDisable(true);
                disconnect.setDisable(false);
                play.setDisable(false);

                onlineState = OnlineState.HOST;
                break;
            case GUEST :
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Connection information : ");
                alert.setHeaderText("Succesful connection");
                //alert.setContentText("You joined a game : \n IP : " + client.getSocket().getInetAddress() + "\n Port : " + server.getSocket().getLocalPort());
                alert.showAndWait();

                host.setDisable(true);
                join.setDisable(true);
                disconnect.setDisable(false);
                play.setDisable(false);

                onlineState = OnlineState.GUEST;
                break;
            case DISCONNECTED :

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Connection information : ");
                alert.setHeaderText("Succesful disconnection");
                alert.setContentText("You are now disconnected from every games.");
                alert.showAndWait();

                host.setDisable(false);
                join.setDisable(false);
                disconnect.setDisable(true);
                play.setDisable(true);

                onlineState = OnlineState.DISCONNECTED;
                break;
            case ERROR :
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection information : ");
                alert.setHeaderText("Failed connection");
                alert.setContentText("A problem seems to have occured...\n Check the IP and the port and retry in a few seconds.");
                alert.showAndWait();

                host.setDisable(false);
                join.setDisable(false);
                disconnect.setDisable(true);
                play.setDisable(true);

                onlineState = OnlineState.DISCONNECTED;
                break;
        }

    }
}
