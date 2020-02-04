package Broadcast;

import GameEntity.Enemy.ChangedKoopa;
import GameEntity.Enemy.Koopa;
import GameEntity.Enemy.KoopaState;
import GameEntity.FireBall;
import GameEntity.OtherPlayerFireBall;
import GameEntity.RedMushroom;
import GameTile.Brick;
import GameTile.FireFlowerSpot;
import GameTile.PowerUpBlock;
import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ReaderClient extends Thread {
    private int port;
    private String host, command = ".";
    private InputStream input;
    private OutputStream output;
    private PrintWriter pr;
    private BufferedReader br;
    private Socket socket;
    private Handler handler;
    public static int otherPlayerX, otherPlayerY, otherPlayerStatus, otherPlayerFrame;
    private ArrayList<ChangedKoopa> changedKoopas = new ArrayList<>();
    private ArrayList<DeadObject> deadObjects = new ArrayList<>();
    private  ArrayList<OtherPlayerFireBall>otherPlayerFireBalls=new ArrayList<>();


    public ReaderClient(int port, String host, Handler h) {
        this.port = port;
        this.host = host;
        handler = h;

    }


    private void receivePlayer() throws Exception {
        command = br.readLine();

        if (command.equals("-1")) {
            otherPlayerStatus = -1;
            return;
        } else {
            //  command = br.readLine();
            otherPlayerStatus = Integer.parseInt(command);

            command = br.readLine();
            otherPlayerFrame = Integer.parseInt(command);

            command = br.readLine();
            otherPlayerX = Integer.parseInt(command);

            command = br.readLine();
            otherPlayerY = Integer.parseInt(command);
        }

    }

    private void receiveLiveKoopas() throws Exception {
        while (true) {
            command = br.readLine();

            if (command.equals("OK"))
                return;

            int tg = Integer.parseInt(command);
            int vel = Integer.parseInt(br.readLine());

            changedKoopas.add(new ChangedKoopa(tg, vel));
        }
    }

    private void receiveDeadThings() throws Exception {
        while (true) {


            command = br.readLine();

            if (command.equals("OK"))
                return;

            int tg;
            int x, y;

            switch (command) {

                case "goomba":
                    tg = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(tg, Id.goomba));
                    break;

                case "hedgehog":
                    tg = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(tg, Id.hedgehog));
                    break;

                case "koopa":
                    tg = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(tg, Id.koopa));
                    break;

                case "plant":
                    tg = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(tg, Id.plant));
                    break;


                case "brick":
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(x, y, Id.brick));
                    break;

                case "coin":
                    //  JOptionPane.showMessageDialog(null, "A DEAD COIN !");
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
                    //      System.out.println("DEAD COINT X: " + x);
                    //    System.out.println("DEAD COINT Y: " + y);
                    deadObjects.add(new DeadObject(x, y, Id.coin));
                    break;


                case "fireFlower":
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(x, y, Id.fireFlower));
                    break;

                case "powerUp":
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
                    int hits = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(x, y, Id.powerUp, hits));
                    break;

                case "redMushroom":
                    tg = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(tg, Id.redMushroom));
                    break;


                case "fireFlowerSpot":
                    x = Integer.parseInt(br.readLine());
                    y = Integer.parseInt(br.readLine());
                    deadObjects.add(new DeadObject(x, y, Id.fireFlowerSpot));
                    break;


            }
        }
    }

    private void receiveFireBalls() throws Exception {
        while (true) {
            command = br.readLine();
            if (command.equals("OK"))
                break;

            int x = Integer.parseInt(command);
            int y = Integer.parseInt(br.readLine());
            int velX=Integer.parseInt(br.readLine());
            int lastX=Integer.parseInt(br.readLine());
            int playerY=Integer.parseInt(br.readLine());

            otherPlayerFireBalls.add(new OtherPlayerFireBall(x,y,velX,lastX,playerY));
            /*
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
             */

        }
    }




    @Override
    public void run() {
        try {


           otherPlayerStatus=-1;

            socket = new Socket(host, port);
            input = socket.getInputStream();
            output = socket.getOutputStream();

            pr = new PrintWriter(new OutputStreamWriter(output));
            br = new BufferedReader(new InputStreamReader(input));

            identify();

            while (!command.equals("DONE")) {

                //  System.out.println("stats : "+otherPlayerStatus);
                //  System.out.println("x : "+otherPlayerX);
                //  System.out.println("y : "+otherPlayerY);

                command = br.readLine();
                //  System.out.println("in reader client command = "+command);

                switch (command) {
                    case "OK":
                        continue;

                    case "PLAYER":
                        receivePlayer();
                        break;

                    case "LIVEKOOPA":
                        receiveLiveKoopas();
                        break;

                    case "DEADTHINGS":
                        receiveDeadThings();
                        break;

                    case "FIRE":
                        receiveFireBalls();
                        break;


//                    case "MUSHROOM":
//                        receiveMushroom();
//                        break;

                    default:
                        //       System.out.println(command);
                        break;


                }


            }

            applyRemoteUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void identify() throws Exception {
        pr.println(Game.playerIndex);
        pr.flush();
    }

    private void updateLiveKoopas() {
        //  System.out.println(changedKoopas.size());

        for (int i = 0; i < changedKoopas.size(); i++) {
            for (int j = 0; j < handler.getEntity().size(); j++) {
                if (handler.getEntity().get(j).getId() == Id.koopa && handler.getEntity().get(j).getTag() == changedKoopas.get(i).getTag()) {
                    if (handler.getEntity().get(j).getKoopaState() == KoopaState.WALKING) {
                        handler.getEntity().get(j).setKoopaState(KoopaState.SHELL);
                        handler.getEntity().get(j).setVelX(0);
                      //  System.out.println("WALKING TO SHELL");
                        break;
                    }

                    if (handler.getEntity().get(j).getKoopaState() == KoopaState.SHELL) {
                        handler.getEntity().get(j).setKoopaState(KoopaState.SPINNING);
                        handler.getEntity().get(j).setVelX(changedKoopas.get(i).getVelX());
                    //    System.out.println("SHELL TO SPIN");
                        break;
                    }
                }
            }

        }
    }

    private void updateDeadObjects() {
        for (int i = 0; i < deadObjects.size(); i++) {
            DeadObject deadObject = deadObjects.get(i);
            //   System.out.println("DEAD OBJECT ID : "+deadObject.getId());

            switch (deadObject.getId()) {
                case goomba:
                    for (int j = 0; j < handler.getEntity().size(); j++)
                        if (handler.getEntity().get(j).getId() == Id.goomba && handler.getEntity().get(j).getTag() == deadObject.getTag()) {
                            handler.getEntity().remove(j);
                            break;
                        }
                    break;

                case hedgehog:
                    for (int j = 0; j < handler.getEntity().size(); j++)
                        if (handler.getEntity().get(j).getId() == Id.hedgehog && handler.getEntity().get(j).getTag() == deadObject.getTag()) {
                            handler.getEntity().remove(j);
                            break;
                        }
                    break;


                case plant:
                    //    System.out.println("DEAD PLANT");
                    for (int j = 0; j < handler.getEntity().size(); j++)
                        if (handler.getEntity().get(j).getId() == Id.plant && handler.getEntity().get(j).getTag() == deadObject.getTag()) {
                            handler.getEntity().remove(j);
                            break;
                        }
                    break;

                case koopa:
                    for (int j = 0; j < handler.getEntity().size(); j++)
                        if (handler.getEntity().get(j).getId() == Id.koopa && handler.getEntity().get(j).getTag() == deadObject.getTag()) {
                            handler.getEntity().remove(j);
                            break;
                        }
                    break;


                case brick:
              //      System.out.println("We got a brick !");
                    for (int j = 0; j < handler.getTile().size(); j++)
                        if (handler.getTile().get(j).getId() == Id.brick && handler.getTile().get(j).getX() == deadObject.getX() && handler.getTile().get(j).getY() == deadObject.getY()) {
                            Brick brick = (Brick) handler.getTile().get(j);
                            brick.die();
                            break;
                        }
                    break;


                case coin:
                    //       System.out.println("REMOVING COIN");
                    for (int j = 0; j < handler.getTile().size(); j++)
                        if (handler.getTile().get(j).getId() == Id.coin && handler.getTile().get(j).getX() == deadObject.getX() && handler.getTile().get(j).getY() == deadObject.getY()) {
                            handler.getTile().remove(j);
                            break;
                        }
                    break;


                case fireFlower:
                    for (int j = 0; j < handler.getTile().size(); j++)
                        if (handler.getTile().get(j).getId() == Id.fireFlower && handler.getTile().get(j).getTag() == deadObject.getTag()) {
                            handler.getTile().remove(j);
                            break;
                        }
                    break;

                case powerUp:
                //    System.out.println("OH DEAR ! WE RECEIVED A POWERUP CHANGE");
                    for (int j = 0; j < handler.getTile().size(); j++)
                        if (handler.getTile().get(j).getId() == Id.powerUp && handler.getTile().get(j).getX() == deadObject.getX() && handler.getTile().get(j).getY() == deadObject.getY()) {
                            ((PowerUpBlock) handler.getTile().get(j)).addHit(false);
                            break;
                        }
                    break;


                case redMushroom:
                    for (int j = 0; j < handler.getEntity().size(); j++)
                        if (handler.getEntity().get(j).getId() == Id.redMushroom && handler.getEntity().get(j).getTag() == deadObject.getTag()) {
                            handler.getEntity().remove(j);
                            break;
                        }


                case fireFlowerSpot:
                    for (int j = 0; j < handler.getTile().size(); j++)
                        if (handler.getTile().get(j).getId() == Id.fireFlowerSpot) {
                            ((FireFlowerSpot) handler.getTile().get(j)).addHit();
                            break;
                        }


            }
        }
    }

    private void updateFireBalls()
    {
        for (int i=0;i<otherPlayerFireBalls.size();i++)
        {
            OtherPlayerFireBall opf=otherPlayerFireBalls.get(i);

            handler.getEntity().add(new FireBall(opf.getX(),opf.getY(),opf.getVelX(),opf.getLastX(),opf.getPlayerY(),handler));

        }

    }


    private void applyRemoteUpdate() {
        updateLiveKoopas();
        updateDeadObjects();
        updateFireBalls();


    }
}

