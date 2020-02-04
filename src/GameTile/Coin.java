package GameTile;

import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class Coin extends Tile {
    public Coin(int x, int y, int width, int height, boolean solid, Id id, Handler handler,int tg) {
        super(x, y, width, height, false, id, handler,tg);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.coin.getBufferedImage(), x, y, width, height, null);
    }

    @Override
    public void tick() {

    }

    public void die()
    {
        handler.deadThings.add(new DeadObject(x,y,id));
        super.die();
    }

}
