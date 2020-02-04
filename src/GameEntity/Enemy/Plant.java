package GameEntity.Enemy;

import GameEntity.Entity;
import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class Plant extends Entity {

    private int wait;
    private int pixelsTraveled = 0,frame=0,frameDelay;

    private boolean moving=false, changeState,insidePipe=true;

    public Plant(int x, int y, int width, int height, Id id, Handler handler,int tg) {
        super(x, y, 50, 50, id, handler,tg);
        moving = false;
        changeState = false;
        velX = 0;
    }

    public boolean isChangeState() {
        return changeState;
    }

    @Override
    public void render(Graphics g) {

//        g.setColor(Color.red);
//        g.drawRect(x,y,width,height);

        if(!moving && !changeState)
        {
            g.drawImage(Game.plant[0].getBufferedImage(),x,y,width,height,null);
            return;
        }

        else
        {
            g.drawImage(Game.plant[frame].getBufferedImage(),x,y,width,height,null);


            frameDelay++;

            if(frameDelay==50)
            {
                frameDelay=0;
                frame++;

                if(frame>1)
                    frame=0;
            }



            return;

        }
    }

    @Override
    public void tick() {
        if(Game.paused)
            return;

        y += velY;

        if (!moving)
            wait++;

        if (wait >= 180) {
            if (changeState)
                changeState = false;

            else
                changeState = true;

            moving = true;

            if(insidePipe)
                insidePipe=false;

            wait = 0;
        }

        if (moving) {
            if (changeState)
                setVelY(-3);
            else
                velY = 3;

            pixelsTraveled += velY;


            if(pixelsTraveled >= getHeight())
                insidePipe=true;

            if (pixelsTraveled >= getHeight() || pixelsTraveled<=-getHeight()) {
                pixelsTraveled = 0;
                moving=false;

                setVelY(0);
            }
        }


    }

    public boolean isInsidePipe() {
        return insidePipe;
    }

    public void die()
    {
        handler.deadThings.add(new DeadObject(tag,id));
        super.die();
    }
}
