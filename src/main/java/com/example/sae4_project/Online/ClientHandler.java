package com.example.sae4_project.Online;

import com.example.sae4_project.Entity.CreatorPlayer;
import com.example.sae4_project.Entity.Player;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler extends Thread{

    private final Server server;
    boolean running = true;

    Socket socket;

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            boolean exist = false;
            while(running){
                    DataMessage msg = (DataMessage) in.readObject();

                System.out.println(server.getPlayers().size());

                    synchronized(server.getPlayers()){
                        for(Player player : server.getPlayers()){
                            if(player.getIdentifier() == msg.getId()){
                                exist = true;
                                if(msg.isSpace()){
                                    player.divideItself();
                                } else {
                                    player.moveTowards(msg.getDx(), msg.getDy(), player.calculateMaxSpeed());
                                }
                            }
                        }

                        if(!exist){
                            Player newPlayer = new CreatorPlayer().create();
                            newPlayer.setIdentifier(msg.getId());
                            server.getPlayers().add(newPlayer);
                        }

                    }
                    
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void disconnect() throws IOException {
        running = false;
    }

    @Override
    public void interrupt() {
        super.interrupt();
        try {
            this.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
