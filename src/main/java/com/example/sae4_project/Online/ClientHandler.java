package com.example.sae4_project.Online;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientHandler extends Thread{

    boolean running = true;

    ObjectInputStream in;

    public ClientHandler(Socket socket) throws IOException {

        this.in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {

            while(running){

                try {

                    Object obj = in.readObject();
                    DataPlayer dataPlayer = (DataPlayer) obj;
                    System.out.println("======================   " + dataPlayer + "   ==================================");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
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
