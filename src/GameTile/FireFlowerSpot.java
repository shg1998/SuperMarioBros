package GameTile;

import GameEntity.RedMushroom;
import GameGFX.Sprite;
import Mario.DeadObject;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.*;
import java.util.Random;


public class FireFlowerSpot extends Tile {
    private Sprite powerUp;
    //   boolean poppedUp = false;
    private int spriteY = getY();
    private boolean normalState = true, contact = false;
    private Random random = new Random();


    public FireFlowerSpot(int x, int y, int width, int height,Id id, Handler handler, int tg) {
        super(x, y, width, height, true, id, handler, tg);
        this.powerUp = Game.dummy;


        int temp = random.nextInt(100);
    }

    public void addHit() {

        if(!activated)
        {
                 contact = true;
                 Handler.deadThings.add(new DeadObject(x,y,Id.fireFlowerSpot));
        }

    }

    @Override
    public void render(Graphics g) {
        if (contact)
            g.drawImage(Game.fireFlower.getBufferedImage(), x, spriteY, width, height, null);

        if (!activated && !contact)
            g.drawImage(Game.powerUp.getBufferedImage(), x, y, width, height, null);

        else {
            g.drawImage(Game.usedPowerUp.getBufferedImage(), x, y, width, height, null);
        }


    }


    @Override
    public void tick() {

        if (Game.paused)
            return;

        if (contact && !activated) {


            spriteY--;

            //INja farz jardi power up ha faghat gharch and
            if (spriteY < y - height) {
//                JOptionPane.showMessageDialog(null,"pre trig");
                //implement flower

                activated = true;
                powerUp = Game.redMushroom;
                handler.addEntity(new FireFlower(x, spriteY, width, height, Id.fireFlower, handler, Handler.fireFlowerTags++));
                contact=false;
            }

        }


    }




}