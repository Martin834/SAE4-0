package com.example.sae4_project.Online;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMessageReceiver extends Thread{


    private Socket socket;
    private boolean running = true;
    private byte[] read;

    public ServerMessageReceiver(Socket socket) {
        this.socket = socket;
    }
    public void run() {
        super.run();

        while(running){
            try {
                read = socket.getInputStream().readAllBytes();
                System.out.println(new String(read));
                
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
