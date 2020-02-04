package GameTile;

import Mario.Handler;
import Mario.Id;

import java.awt.*;

public abstract class Tile {
    protected int x,y,width,height,velX,velY,facing,tag;
    protected boolean solid,activated=false;
    protected Id id;
    protected Handler handler;

    public Tile(int x, int y, int width, int height,boolean solid,Id id,Handler handler,int tg) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid=solid;
        this.id=id;
        this.handler=handler;
        tag=tg;

    }

    /**
     * Instead of creating bufferStrategies we use grapgics so
     * the game will not lag due to many objects displayed in the screen
     * @param g
     */
    public abstract void render(Graphics g);


    /**
     * Update this entity
     */
    public abstract void tick();

    public void die()
    {
        handler.removeTile(this);
    }

    public Id getId() {
        return id;
    }

    public boolean getSolid(){
        return solid;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(getX(),getY(),width,height);
    }

    public int getTag() {
        return tag;
    }
}
