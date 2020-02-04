package GameEntity;

import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;

public class GreenMushroom extends RedMushroom {
    public GreenMushroom(int x, int y, int width, int height, Id id, Handler handler,int tg) {
        super(x, y, width, height, id, handler,tg);
    }

    public  void render(Graphics g){
        g.drawImage(Game.greenMushroom.getBufferedImage(),x,y,width,height,null);
    }
}
