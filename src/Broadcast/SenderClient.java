package Broadcast;
//Aproach one. First we do not seprate destroyable tile and non-destroyable tile. deleting takes longer time of course. for ticking and drawing both

import GameEntity.*;
import GameEntity.Enemy.*;
import GameTile.Brick;
import GameTile.Coin;
import GameTile.FireFlower;
import GameTile.PowerUpBlock;
import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
///Destroyed entity

public class SenderClient extends Thread {
    private Handler handler;
    private String host;
    private int port;
    private Socket socket;
    private OutputStream output;
    private InputStream input;
    private PrintWriter pr;
    private BufferedReader br;
    private static Entity e;

    public SenderClient(Handler handler, String host, int port) {
        this.handler = handler;
        this.host = host;
        this.port = port;


    }

    @Override
    public void run() {

        try {
            socket = new Socket(host, port);
            output = socket.getOutputStream();
            input = socket.getInputStream();
            pr = new PrintWriter(new OutputStreamWriter(output));
            input = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(input));

            br.readLine();
            identify();
            send();
            //   br = new BufferedReader(new InputStreamReader(input));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void identify() {
        pr.println(Game.playerIndex + "");
        pr.flush();
        //   System.out.println("in sender client identified");

    }

    private void send() {
        //PLAYER
        pr.println("PLAYER");
        pr.flush();
        sendPlayer();
        //  pr.println("OK");
        // pr.flush();


        //HANDLE LIVE KOOPA :|
        pr.println("LIVEKOOPA");
        pr.flush();

        for (int i = 0; i < Handler.changedLiveKoopas.size(); i++)
            sendChangedKoopa(Handler.changedLiveKoopas.get(i));

        pr.println("OK");
        pr.flush();


        pr.println("DEADTHINGS");
        pr.flush();

        //Handle dead things
        for (int i = 0; i < Handler.deadThings.size(); i++) {
            Id dummy = Handler.deadThings.get(i).getId();
            DeadObject deadObject = Handler.deadThings.get(i);

            pr.println(deadObject.getId() + "");
            pr.flush();

            switch (dummy) {
                ////////DEAD ENTITY
                case goomba:
                    sendDeadGoomba(deadObject);
                    break;

                case hedgehog:
                    sendDeadHedgehog(deadObject);
                    break;

                case koopa:
                    sendDeadKoopa(deadObject);
                    break;

                case plant:
                    sendDeadPlant(deadObject);
                    break;


                ////DEAD TILE
                case brick:
                    sendBrokeBrick(deadObject);
                    break;

                case coin:
                    sendUsedCoin(deadObject);
                    break;

                case fireFlower:
                    sendUsedFlower(deadObject);
                    break;


                case powerUp:
                    sendChangedPowerUp(deadObject);
                    break;

                case redMushroom:
                    sendDeadMushroom(deadObject);
                    break;


                case fireFlowerSpot:
                    sendUsedFireFlowerSpot(deadObject);
                    break;

            }
        }

        pr.println("OK");
        pr.flush();

        pr.println("FIRE");
        pr.flush();


    //    System.out.println("Sent FB SIZE "+Handler.fireBalls.size());

        for (int i = 0; i < Handler.otherPlayerFireBalls.size(); i++) {
            OtherPlayerFireBall f = Handler.otherPlayerFireBalls.get(i);

            pr.println(f.getX());
            pr.flush();

            pr.println(f.getY());
            pr.flush();

            pr.println(f.getVelX());
            pr.flush();

            pr.println(f.getLastX());
            pr.flush();

            pr.println(f.getPlayerY());
            pr.flush();
        }

        pr.println("OK");
        pr.flush();


        pr.println("DONE");
        pr.flush();
    }

    private void sendChangedKoopa(ChangedKoopa changedKoopa) {
        pr.println(changedKoopa.getTag() + "");
        pr.flush();

        pr.println(changedKoopa.getVelX() + "");
        pr.flush();
    }


    private void sendPlayer() {
        //-1 DEAD
        if (Game.lives > 0) {
            pr.println(Player.status);
            pr.flush();

            pr.println(Player.getFrame());
            pr.flush();

            pr.println(Handler.player.getX());
            pr.flush();

            pr.println(Handler.player.getY());
            pr.flush();
        } else {
            pr.println(-1);
            pr.flush();
        }

    }

//    private void sendLiveKoopa(DeadObject koopa) {
//        pr.println(koopa.getTag());
//        pr.flush();
//
//        //0 WALKING ,1 SHELL , 2 SPINING
//        //This is normal state
//        //no state requires a frame!
//        if (koopa.getKoopaState() == KoopaState.WALKING)
//            pr.println(0);
//
//        if (koopa.getKoopaState() == KoopaState.SHELL)
//            pr.println(1);
//
//
//        if (koopa.getKoopaState() == KoopaState.SPINNING)
//            pr.println(2);
//
//        pr.flush();
//
////        pr.println(koopa.getFrame());
////        pr.flush();
//    }

    private void sendDeadKoopa(DeadObject koopa) {
        pr.println(koopa.getTag());
        pr.flush();
    }


    private void sendDeadGoomba(DeadObject goomba) {
        pr.println(goomba.getTag());
        pr.flush();
    }


    private void sendDeadHedgehog(DeadObject hedgehog) {
        pr.println(hedgehog.getTag());
        pr.flush();
    }

    private void sendDeadPlant(DeadObject plant) {
        pr.println(plant.getTag());
        pr.flush();
    }


    private void sendBrokeBrick(DeadObject brick) {

        pr.println(brick.getX());
        pr.flush();

        pr.println(brick.getY());
        pr.flush();
    }

    private void sendUsedFireFlowerSpot(DeadObject fireFlowerSpot) {
        pr.println(fireFlowerSpot.getX());
        pr.flush();

        pr.println(fireFlowerSpot.getY());
        pr.flush();
    }


    private void sendUsedFlower(DeadObject fireFlower) {
        pr.println(fireFlower.getX());
        pr.flush();

        pr.println(fireFlower.getY());
        pr.flush();
    }

    private void sendUsedCoin(DeadObject coin) {
        pr.println(coin.getX());
        pr.flush();

        pr.println(coin.getY());
        pr.flush();
    }

    private void sendChangedPowerUp(DeadObject powerUpBlock) {
        pr.println(powerUpBlock.getX());
        pr.flush();

        pr.println(powerUpBlock.getY());
        pr.flush();

        pr.println(powerUpBlock.getHits());
        pr.flush();
    }


    private void sendFireBall(DeadObject fireBall) {
        pr.println(fireBall.getX());
        pr.flush();

        pr.println(fireBall.getY());
        pr.flush();
    }

    private void sendNewRedMushroom(DeadObject redMushroom) {
        pr.println(redMushroom.getX());
        pr.flush();

        pr.println(redMushroom.getY());
        pr.println();
    }

    private void sendDeadMushroom(DeadObject redMushroom) {
        pr.println(redMushroom.getTag());
        pr.flush();
    }

}
