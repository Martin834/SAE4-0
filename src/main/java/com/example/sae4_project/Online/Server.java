package com.example.sae4_project.Online;

import com.example.sae4_project.Application.AgarioApplication;
import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;
import com.example.sae4_project.QuadTree.Map;

import java.io.IOException;
import java.io.ObjectOutputStream;
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

    public ArrayList<ObjectOutputStream> getClientWriters() {
        return clientWriters;
    }

    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    private UpdateWorld updateWorld;
    private ArrayList<ObjectOutputStream> clientWriters = new ArrayList<>();


    public ArrayList<Pellet> getPellets() {
        return pellets;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    private ArrayList<Pellet> pellets = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();


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

        Map map = Map.getInstance();
        map.getAllPellet(map.getQuadTree(), pellets);


    }

    public void addClient(Socket client) throws IOException {


        System.out.println("connected");
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        synchronized (this.clientWriters){
            clientWriters.add(out);
        }

        ClientHandler clientHandler = new ClientHandler(this, client);
        AgarioApplication.threads.add(clientHandler);
        clientHandler.start();
    }

    public void disconnect() throws IOException {
        serverManager.disconnect();
        updateWorld.disconnect();
        for(ClientHandler client : clientHandlers){
            client.disconnect();
        }
    }
}
