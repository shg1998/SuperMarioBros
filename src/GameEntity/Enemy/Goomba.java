package GameEntity.Enemy;

import GameEntity.Entity;
import GameGFX.Sprite;
import GameTile.Tile;
import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import javax.swing.*;
import java.awt.*;

public class Goomba extends Entity {
    private boolean isDying=false,animate=false;
    private int frame=0,frameDelay=0,helpFrame,diecounter;

    public void setDying(boolean dying) {
        isDying = dying;
    }


    public Goomba(int x, int y, int width, int height, boolean solid, Id id, Handler handler,int tg) {
        super(x, y, width, height, id, handler,tg);
        velX=1;
    }

    @Override
    public void render(Graphics g)
    {
//        if(isDying)
//        {
//
//            g.drawImage(Game.goomba[2].getBufferedImage(), x, y, width, height, null);
//
//
//
//            return;
//        }
        g.drawImage(Game.goomba[helpFrame].getBufferedImage(), x, y, width, height, null);


        frameDelay++;

        if(frameDelay==100)
        {
            helpFrame++;
            frameDelay=0;
        }

        if(helpFrame==2)
            helpFrame=0;



        return;

//   //     if (facing == 0)
//           g.drawImage(Game.goomba[frame + 4].getBufferedImage(), x, y, width, height, null);
//
//        else if (facing == 1)
//            g.drawImage(Game.goomba[frame].getBufferedImage(), x, y, width, height, null);



    }

    public boolean getDying()
    {
        return isDying;
    }

    public void tick()
    {
        if(Game.paused)
            return;

        x+=velX;
        y+=velY;

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
                setVelX(1);
            }

            if (getBoundsRight().intersects((t.getBounds()))) {
                setVelX(-1);

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
//
//        if (animate) {
//
//            frameDelay++;
//
//            if (frameDelay >= 3) {
//                frame++;
//
//                if (frame >= 4) {
//                    frame = 0;
//                    frameDelay = 0;
//                }
//
//            }
//        } else if (facing == 1)
//            frame = 0;
//
//        else
//            frame = 3;

        for (int i=0;i<handler.getEntity().size();i++)
            if(handler.getEntity().get(i).getId()==Id.koopa && !(handler.getEntity().get(i).equals(this)) && (handler.getEntity().get(i).getBounds().intersects(this.getBounds())) && (handler.getEntity().get(i).getKoopaState()==KoopaState.SPINNING))
            {
                die();
                break;
            }



    }

    public void die()
    {
        handler.deadThings.add(new DeadObject(tag,id));
        super.die();
    }


}
