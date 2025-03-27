package com.example.sae4_project.Online;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread{

    boolean running = true;

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

    byte[] read = new byte[4096];

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

           /* int bytesRead = 0;

            while(running){
                try {
                    bytesRead = this.socket.getInputStream().read(read);
                    System.out.println(new String(read, 0, bytesRead));

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }*/

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
