package com.example.sae4_project.Online;

import com.example.sae4_project.Application.AgarioApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;

public class ServerManager extends Thread {

    public ServerSocket getSocket() {
        return socket;
    }

    private ServerSocket socket;
    private Server server;
    private boolean running;

    public ServerManager(Server server) {
        this.server = server;
    }

    public void run() {
        super.run();
        try {

            socket = new ServerSocket(834);

            while(running){
                Socket income = socket.accept();
                this.server.addClient(income);

            }

            socket.close();

        } catch (IOException e) {
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
