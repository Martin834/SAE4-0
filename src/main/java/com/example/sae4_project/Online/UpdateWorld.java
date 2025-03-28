package com.example.sae4_project.Online;

import com.example.sae4_project.Entity.Pellet;
import com.example.sae4_project.Entity.Player;
import com.example.sae4_project.QuadTree.Map;
import javafx.scene.shape.Circle;

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
                        writer.writeObject(new DataMap(server));
                        writer.flush();
                    }
                }

                synchronized(server.getPlayers()){


                    for(Player player : server.getPlayers()){

                        player.move();

                        Pellet touchedPellet;
                        synchronized (server.getPellets()) {
                            touchedPellet = player.detectPellet(server.getPellets());

                            if (touchedPellet != null) {
                                for (Circle circle : player.getCirclesList()) {
                                    if (circle.getBoundsInLocal().intersects(touchedPellet.getCircle().getBoundsInLocal())) {
                                        player.makeFatter(touchedPellet, circle);
                                        server.getPellets().remove(touchedPellet);
                                        break;
                                    }
                                }
                            }
                        }
                        touchedPellet = null;
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
