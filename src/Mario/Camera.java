package Mario;

import GameEntity.Entity;
import GameEntity.Player;

public class Camera {
    private int x=0,y=0,lastX=0;
    private Player temp;

    public void tick(Entity player)
    {
        setX(-player.getX()+ Game.WITDH*2);
        setY(-player.getY() +Game.HEIGHT*2 );
        temp=(Player)player;
    }

    public int getPlayerX()
    {
        return temp.getX();
    }

    public int getPlayerY()
    {
        return temp.getY();
    }


    public int getX() {
        if(x>lastX)
            return lastX;

        lastX=x;
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLastX()
    {
        return lastX;
    }
}
