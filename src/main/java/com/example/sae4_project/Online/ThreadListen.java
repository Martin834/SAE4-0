package com.example.sae4_project.Online;

import java.net.ServerSocket;

public class ThreadListen extends Thread{

    private ServerSocket server;
    public ThreadListen(ServerSocket serverSocket){
        this.server = serverSocket;
    }
    @Override
    public void run() {
        for(int i = 0; i < 1000; i++){
            System.out.println(server);
        }

    }
}
