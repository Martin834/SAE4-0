package com.example.sae4_project.Online;

import com.example.sae4_project.Application.AgarioApplication;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public ServerManager getServerManager() {
        return serverManager;
    }

    private ServerManager serverManager;
    private ArrayList<ClientHandler> clientHandlers;

    private ArrayList<Socket> clients = new ArrayList<>();
    public Server() {

    }

    public void start(){
        serverManager = new ServerManager(this);
        serverManager.start();
        AgarioApplication.threads.add(serverManager);
    }

    public void addClient(Socket client){
        this.clients.add(client);
    }

    public void disconnect() throws IOException {
        serverManager.disconnect();
    }
}
