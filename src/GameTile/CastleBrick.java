package GameTile;

import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class CastleBrick extends Tile {
    public CastleBrick(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler,0);
    }

    public void render(Graphics g)
    {
        g.drawImage(Game.castleBrick.getBufferedImage(),x,y,width,height,null);
    }

    public void tick()
    {

    }
}
