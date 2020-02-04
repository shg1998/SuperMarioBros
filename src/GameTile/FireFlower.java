package GameTile;

import GameEntity.Entity;
import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class FireFlower extends Entity {

    public boolean popped=false;

    //handle it
    public FireFlower(int x, int y, int width, int height, Id id, Handler handler,int tg) {
        super(x, y, width, height, id, handler,tg);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.fireFlower.getBufferedImage(),x,y,width,height,null);
    }

    @Override
    public void tick() {

    }

    public void die()
    {
        handler.deadThings.add(new DeadObject(x,y,id));
        super.die();
    }
    public boolean isPopped() {
        return popped;
    }
}
