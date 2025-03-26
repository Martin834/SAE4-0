package com.example.sae4_project.Online;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{

    boolean running;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public byte[] getRead() {
        return read;
    }

    public void setRead(byte[] read) {
        this.read = read;
    }

    Socket socket;

    byte[] read;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        running = true;
    }

    @Override
    public void run() {

            while(true){
                try {
                    read = this.socket.getInputStream().readAllBytes();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

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
