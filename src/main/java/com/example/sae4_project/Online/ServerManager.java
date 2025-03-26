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

    public void setSocket(ServerSocket socket) {
        this.socket = socket;
    }

    public ArrayList<PrintWriter> getGuests() {
        return guests;
    }

    public void setGuests(ArrayList<PrintWriter> guests) {
        this.guests = guests;
    }

    private ServerSocket socket;
    private ArrayList<PrintWriter> guests;
    private boolean running;

    public ServerManager() {
    }

    public void run() {
        super.run();
        try {

            this.socket = new ServerSocket();

            InetAddress adresseServer = InetAddress.getLocalHost();
            this.socket.bind(new InetSocketAddress(adresseServer, 834));

            while(running){
                Socket guest = this.socket.accept();
                ClientHandler th = new ClientHandler(guest);
                AgarioApplication.threads.add(th);
                th.start();
                this.guests.add(new PrintWriter(th.getSocket().getOutputStream()));
                System.out.println(guests.size());
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
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
