package com.example.sae4_project.Online;

import com.example.sae4_project.QuadTree.Map;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class UpdateWorld extends Thread{

    private boolean running = true;

    private Server server;

    public UpdateWorld(Server server){
        this.server = server;
    }



    @Override
    public void run() {
        super.run();
        while(running){
            try {
                this.sleep(33);

                synchronized(server.getClientWriters()){

                    for(ObjectOutputStream writer : server.getClientWriters()){
                        System.out.println(writer.toString());
                        writer.writeObject(new DataMap());
                        writer.flush();
                    }
                }


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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

    /*public static void main(String[] args) {
        UpdateWorld u = new UpdateWorld(null);
        u.start();
    }*/
}
