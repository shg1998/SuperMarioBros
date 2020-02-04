package GameTile;

import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class CastleDoor extends Tile {

    public CastleDoor(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler,0);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.castleDoor.getBufferedImage(),x,y,width,height,null);
    }

    @Override
    public void tick() {

    }
}
