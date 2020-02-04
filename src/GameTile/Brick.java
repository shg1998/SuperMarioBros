package GameTile;

import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class Brick extends  Tile {
    private int deathCounter=0;

    public Brick(int x, int y, int width, int height, boolean solid, Id id, Handler handler,int tg) {
        super(x, y, width, height, solid, id, handler,tg);
    }

    @Override
    public void render(Graphics g) {

        if(deathCounter==0)
        g.drawImage(Game.ordinaryBrick.getBufferedImage(),x,y,width,height,null);

        else
        {
            g.drawImage(Game.destroyedOrdinaryBrick.getBufferedImage(),x,y,width,height,null);
            deathCounter++;

            if(deathCounter==60)
                super.die();
        }
    }

    public void die()
    {
        handler.deadThings.add(new DeadObject(x,y,id));
        super.die();
        deathCounter++;
    }

    @Override
    public void tick() {

    }
}
