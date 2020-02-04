package GameTile;

import GameEntity.Enemy.Plant;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class Pipe extends Tile {
    public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler , int facing,boolean plant,int tg) {
        super(x, y, width, height, solid, id, handler,tg);
        this.facing=facing;


        if(plant)
            handler.addEntity(new Plant(getX(),getY(),getWidth(),64,Id.plant,handler,tg));
    }

    @Override
    public void render(Graphics g)
    {
        g.setColor(Color.green);
        g.fillRect(x,y,width,height);

    }

    @Override
    public void tick()
    {

    }
}
