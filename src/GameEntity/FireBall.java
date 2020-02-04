package GameEntity;

import Broadcast.ReaderClient;
import GameTile.Tile;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;
import java.util.ArrayList;

public class FireBall extends Entity {
    private int numberOfCollisions = 0, lastX, playerY;
    public static int fireBallsInScreen = 0;
    private boolean mine;

    public FireBall(int x, int y, int width, int height, Id id, Handler handler, int facing) {
        super(x, y, width, height, id, handler, 0);
        mine=true;
        Player.liveFireBalls++;
    //    System.out.println("Added a fireball");

        if (facing == 0) {
            velX = -8;
            Handler.otherPlayerFireBalls.add(new OtherPlayerFireBall(x, y, -8, Game.cam.getLastX(), Game.cam.getPlayerY()));
        } else {
            velX = 8;
            Handler.otherPlayerFireBalls.add(new OtherPlayerFireBall(x, y, 8, Game.cam.getLastX(), Game.cam.getPlayerY()));
        }

    }

    public FireBall(int x, int y, int velX, int lastX, int playerY, Handler handler) {
        super(x, y, 24, 24, Id.fireBall, handler, 0);
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.lastX = lastX;
        this.playerY = playerY;
        mine=false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.fireBall.getBufferedImage(), x, y, width, height, null);

//        System.out.println("RECEIVED FB SIZE : "+ReaderClient.fireBallX.size());
//        for (int i = 0; i < ReaderClient.fireBallX.size(); i++)
//            g.drawImage(Game.fireBall.getBufferedImage(), ReaderClient.fireBallX.get(i), ReaderClient.fireBallY.get(i), width, height, null);


    }

    @Override
    public void tick() {
        if (Game.paused)
            return;

        x += velX;
        y += velY;

        System.out.println(x);
        System.out.println(y);
        System.out.println();


//        System.out.println("Game cam lastx "+Game.cam.getLastX());
//        System.out.println("Game cam getY "+Game.cam.getY());
//        System.out.println("x "+x);
//        System.out.println("Y "+y);

        //|| y>Math.abs(Game.cam.getY())+400 || y<Math.abs(Game.cam.getY())-400

        int lastY = Math.abs(Game.cam.getY());

        if (mine && (x < Math.abs(Game.cam.getLastX()) || x > Math.abs(Game.cam.getLastX()) + 1400 || y > Game.cam.getPlayerY() + 400 || y < Game.cam.getPlayerY() - 400)) {
//            System.out.println(lastY);
//            System.out.println(y);
//            System.out.println();
            //     Player.liveFireBalls--;
            System.out.println("DIE");
            die();
            return;
        }

        if (!mine && (x < Math.abs(lastX) || x > Math.abs(lastX) + 1400 || y > playerY + 400 || y < playerY - 400)) {
            die();
            return;
        }

        for (int i = 0; i < handler.getTile().size(); i++) {
            Tile t = handler.getTile().get(i);

            if (t.getSolid() == false)
                continue;

            if (t.getId() == Id.redMushroom || t.getId() == Id.greenMushroom || t.getId() == Id.coin || t.getId() == Id.fireFlower || t.getId() == Id.coin)
                continue;

            if (getBoundsLeft().intersects(t.getBounds()) || getBoundsRight().intersects(t.getBounds())) {
                die();
                //  Player.liveFireBalls--;
                return;
            }

            if (getBoundsBottom().intersects(t.getBounds())) {
                numberOfCollisions++;


                if (numberOfCollisions == 5) {
                    die();
                    //      Player.liveFireBalls--;
                    return;
                }


                jumping = true;
                falling = false;
                gravity = 4.0;
            } else if (!falling && !jumping) {
                falling = true;
                gravity = 1.0;
            }


        }

        for (int i = 0; i < handler.getEntity().size(); i++) {
            Entity e = handler.getEntity().get(i);

            if (e.getId() == Id.goomba || e.getId() == Id.koopa || e.getId() == Id.hedgehog) {
                if (getBoundsBottom().intersects(e.getBounds())) {

                    if (mine)
                        e.die();

                    die();
                    //  Player.liveFireBalls--;
                    return;
                }
            }
        }
        if (jumping) {
            gravity -= .3;
            setVelY((int) -gravity);
            //JOptionPane.showMessageDialog(null,gravity);

            if (gravity <= 0.0) {
                jumping = false;
                falling = true;
            }
        }

        if (falling) {
            gravity += .3;
            setVelY((int) gravity);
        }

    }


    @Override
    public void die() {
        super.die();

        if(mine)
        Player.liveFireBalls--;

    }


}


