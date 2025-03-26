package com.example.sae4_project.Online;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;

public class Server {

    ServerSocket server;

    public Server() {
    }

    public void start() throws IOException {
        this.server = new ServerSocket();

        InetAddress adresseServer = InetAddress.getLocalHost();
        this.server.bind(new InetSocketAddress(adresseServer, 834));

        new ThreadListen(this.server).start();

    }

    public void listen(){
        System.out.println("test");
    }
}
