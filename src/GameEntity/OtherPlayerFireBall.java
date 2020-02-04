package GameEntity;

public class OtherPlayerFireBall {
    private int x, y, velX,lastX,playerY;


    public OtherPlayerFireBall(int x, int y, int velX, int lastX, int playerY) {
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.lastX = lastX;
        this.playerY = playerY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVelX() {
        return velX;
    }

    public int getLastX() {
        return lastX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
