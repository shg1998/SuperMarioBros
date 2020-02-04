package GameTile;

import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class Hole extends Tile{
    public Hole(int x, int y, int width, int height, boolean solid, Id id, Handler handler,int tg) {
        super(x, y, width, height, solid, id, handler,0);
    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public void tick() {

    }
}
