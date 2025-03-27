package com.example.sae4_project.Online;

import com.example.sae4_project.Application.AgarioApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public ServerManager getServerManager() {
        return serverManager;
    }

    private ServerManager serverManager;

    public UpdateWorld getUpdateWorld() {
        return updateWorld;
    }

    public ArrayList<PrintWriter> getClientWriters() {
        return clientWriters;
    }

    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    private UpdateWorld updateWorld;
    private ArrayList<PrintWriter> clientWriters = new ArrayList<>();

    private ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public Server() {

    }

    public void start(){
        serverManager = new ServerManager(this);
        serverManager.start();
        AgarioApplication.threads.add(serverManager);

        updateWorld = new UpdateWorld(this);
        updateWorld.start();
        AgarioApplication.threads.add(updateWorld);

    }

    public void addClient(Socket client) throws IOException {

        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        synchronized (this.clientWriters){
            clientWriters.add(out);
        }

        ClientHandler clientHandler = new ClientHandler(client);
        clientHandler.start();
        AgarioApplication.threads.add(clientHandler);
    }

    public void disconnect() throws IOException {
        serverManager.disconnect();
        updateWorld.disconnect();
        for(ClientHandler client : clientHandlers){
            client.disconnect();
        }
    }
}
