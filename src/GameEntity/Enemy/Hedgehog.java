package GameEntity.Enemy;

import GameEntity.Entity;
import GameTile.Tile;
import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;
import java.util.Random;

public class Hedgehog extends Entity {
    private int helpFrame, frameDelay;
    private boolean animate = false;


    public Hedgehog(int x, int y, int width, int height, Id id, Handler handler,int tg) {
        super(x, y, width, height, id, handler,tg);
        velX = -3;


    }



    @Override
    public void render(Graphics g) {


        if (velX > 0)
            g.drawImage(Game.hedgehog[helpFrame].getBufferedImage(), x, y, width, height, null);

        else
            g.drawImage(Game.hedgehog[helpFrame + 2].getBufferedImage(), x, y, width, height, null);


        frameDelay++;

        if (frameDelay == 100) {
            helpFrame++;
            frameDelay = 0;
        }

        if (helpFrame == 2)
            helpFrame = 0;


        return;


    }

    @Override
    public void tick() {

        if(Game.paused)
            return;

        x += velX;
        y += velY;

        if (velX != 0)
            animate = true;

        else
            animate = false;


        for (Tile t : handler.getTile()) {
            if (t.getSolid()==false)
                continue;

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
                    setVelX(4);
            }

            if (getBoundsRight().intersects((t.getBounds()))) {
                if (koopaState == KoopaState.WALKING)
                    setVelX(-2);

                else
                    setVelX(-4);

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
        ;
        for (int i = 0; i < handler.getEntity().size(); i++)
            if ((handler.getEntity().get(i).getId() == Id.koopa && (handler.getEntity().get(i).getBounds().intersects(this.getBounds())) && (handler.getEntity().get(i).getKoopaState() == KoopaState.SPINNING)) ) {
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
