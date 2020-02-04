package Mario;

import Mario.Id;

public class DeadObject {
    private int tag,x,y,hits;
    private Id id;

    public DeadObject(int tag, Id id) {
        this.tag = tag;
        this.id = id;
    }

    public DeadObject(int x, int y, Id id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public DeadObject(int x,int y,Id id,int hits)
    {
        this.x=x;
        this.y=y;
        this.id=id;
        this.hits=hits;


    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHits() {
        return hits;
    }

    public int getTag() {
        return tag;
    }

    public Id getId() {
        return id;
    }
}
