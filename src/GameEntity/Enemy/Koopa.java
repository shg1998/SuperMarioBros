package GameEntity.Enemy;

import GameEntity.Entity;
import GameTile.Tile;
import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Koopa extends Entity {
    private boolean animate = false;
    private boolean isDying = false;
    private int shellCount;
    private int frame = 0, frameDelay = 0, helpFrame, facing;


    @Override
    public void setFacing(int facing) {
        this.facing = facing;
    }

    public Koopa(int x, int y, int width, int height, Id id, Handler handler,int tg) {
        super(x, y, width, height, id, handler,tg);
        tag=tg;

        velX = -2;
        koopaState = KoopaState.WALKING;

    }

    @Override
    public void render(Graphics g) {
        if (koopaState != KoopaState.WALKING) {
            g.drawImage(Game.koopa[4].getBufferedImage(), x, y, width, height, null);
            return;
        }
//        if (isDying) {
//
//            g.drawImage(Game.koopa[4].getBufferedImage(), x, y, width, height, null);
//
//            die();
//
//            return;
//        }

        //go to right
        if (velX > 0)
            g.drawImage(Game.koopa[helpFrame].getBufferedImage(), x, y, width, height, null);

        else
            g.drawImage(Game.koopa[helpFrame + 2].getBufferedImage(), x, y, width, height, null);


        frameDelay++;

        if (frameDelay == 100) {
            helpFrame++;
            frameDelay = 0;
        }

        if (helpFrame == 2)
            helpFrame = 0;


        return;


    }

    public void tick() {


        if(Game.paused)
            return;


        if (koopaState == KoopaState.SHELL) {
            velX = 0;
            velY = 0;
        }

        x += velX;
        y += velY;

        if (velX != 0)
            animate = true;

        else
            animate = false;


        for (Tile t : handler.getTile()) {
            if (t.getSolid()==false)
                continue;
//            if (t.getId() == Id.wall) {
//                if (getBoundsTop().intersects(t.getBounds())) {
//                    setVelY(0);
//
//                    if (jumping) {
//                        jumping = false;
//                        //  gravity = 0.8;
//                        gravity = 2;
//                        falling = true;
//                    }
////                    y=t.getY()+t.getHeight();
//                    //BLANK
//                }
//            }

            if (getBoundsBottom().intersects((t.getBounds()))) {
                setVelY(0);
                y = t.getY() - t.getHeight();
                if (falling)
                    falling = false;
            } else {
                if (!falling) {
                    //   gravity = 0.8;
                    gravity = 2;
                    falling = true;
                }
            }


            if (getBoundsLeft().intersects((t.getBounds()))) {
                if (koopaState == KoopaState.WALKING)
                    setVelX(2);

                else
                    setVelX(6);
            }

            if (getBoundsRight().intersects((t.getBounds()))) {
                if (koopaState == KoopaState.WALKING)
                    setVelX(-2);

                else
                    setVelX(-6);

            }

        }

        if (falling) {
            gravity += .01;
            setVelY((int) gravity);

        }

        if (jumping) {
            gravity -= 0.1;
            setVelY((int) -gravity);
            //JOptionPane.showMessageDialog(null,gravity);

            if (gravity <= 0.0) {
                jumping = false;
                falling = true;
            }
        }

        if (falling) {
            gravity += .01;
            setVelY((int) gravity);
        }

        for (int i = 0; i < handler.getEntity().size(); i++)
            if (handler.getEntity().get(i).getId() == Id.koopa && !(handler.getEntity().get(i).equals(this)) && (handler.getEntity().get(i).getBounds().intersects(this.getBounds())) && (handler.getEntity().get(i).getKoopaState() == KoopaState.SPINNING)) {
                die();
                break;
            }

    }

    public void die()
    {
       handler.deadThings.add(new DeadObject(tag,id));
       super.die();
    }

    public void statusChanged()
    {
        Handler.changedLiveKoopas.add(new ChangedKoopa(tag,velX));
    }





}
