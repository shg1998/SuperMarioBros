package GameEntity.Enemy;

public class ChangedKoopa {

    private int velX,tag;

    public ChangedKoopa( int tag,int velX) {
        this.velX = velX;
        this.tag=tag;
    }




    public int getVelX() {
        return velX;
    }

    public int getTag() {
        return tag;
    }
}
