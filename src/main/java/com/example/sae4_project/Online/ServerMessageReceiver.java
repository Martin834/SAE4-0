package com.example.sae4_project.Online;

import com.example.sae4_project.QuadTree.Map;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMessageReceiver extends Thread{


    private Socket socket;
    private boolean running = true;

    public DataMap getLastDataMap() {
        return lastDataMap;
    }

    private DataMap lastDataMap;

    public ServerMessageReceiver(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        super.run();
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while(running){

                    this.sleep(33);
                    System.out.println("En attente d'un message... ");
                    Object objRecu = in.readObject();
                    lastDataMap = (DataMap) objRecu;
                    System.out.println(objRecu);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
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
