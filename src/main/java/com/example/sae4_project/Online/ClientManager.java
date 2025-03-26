package com.example.sae4_project.Online;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientManager extends Thread{

    Socket socket;
    boolean running;

    public ClientManager(String host, int port) throws IOException {
        System.out.println("1");
        socket = new Socket(host, port);
        System.out.println("2");
    }

    @Override
    public void run() {
        super.run();
    }

    public void disconnect() throws IOException {
        this.socket.close();
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
