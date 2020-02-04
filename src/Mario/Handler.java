package Mario;

import GameEntity.Enemy.ChangedKoopa;
import GameEntity.Enemy.Goomba;
import GameEntity.Enemy.Hedgehog;
import GameEntity.Enemy.Koopa;
import GameEntity.Entity;
import GameEntity.FireBall;
import GameEntity.OtherPlayerFireBall;
import GameEntity.Player;
import GameTile.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Handler {
    private ArrayList<Entity> entity = new ArrayList<>();
    private ArrayList<Tile> tile = new ArrayList<>();
    public static Player player;
    public static ArrayList<Integer> changedKoopaTags, addedRedMushroomX, addedRedMushroomY;
    public static ArrayList<DeadObject> deadThings;
    public static ArrayList<ChangedKoopa> changedLiveKoopas;
    public static ArrayList<OtherPlayerFireBall>otherPlayerFireBalls=new ArrayList<>();
    public static int koopaTags = 0, goombaTags = 0, plantTags = 0, mushroomTags = 0,hedgeHogTags=0,fireFlowerTags=0,fireBallTags=0;
    //Dead tiles: Only x,y

    public Handler() {
//        createLevel();
    }


    public void render(Graphics g) {
        // System.out.println(Game.getVisibleArea());
        for (int i = 0; i < entity.size(); i++)
            entity.get(i).render(g);
        //    if(Game.getVisibleArea() != null && en.getBounds().intersects(Game.getVisibleArea()))


        for (int i = 0; i < tile.size(); i++)
            tile.get(i).render(g);

        //     if(Game.getVisibleArea() != null && tl.getBounds().intersects(Game.getVisibleArea()))

    }

    public void tick() {
//        for (Entity en : entity)
//            en.tick();



//        for (Tile tl : tile)
//            tl.tick();

        for (int i = 0; i < entity.size(); i++)
            entity.get(i).tick();


        for (int i = 0; i < tile.size(); i++)
            //    if(Game.getVisibleArea() != null && tile.get(i).getBounds().intersects(Game.getVisibleArea()))
            tile.get(i).tick();
    }

    public void createLevel(BufferedImage level) {
//        //64 * 64 pixels for walls is asumptions
//        for (int i = 0; i < Game.WITDH * Game.SCALE / 64 + 10; i++) {
//            addTile(new Wall(i * 64, Game.HEIGHT * Game.SCALE - 64, 64, 64, true, Id.wall, this));
//
//            if(i!=0 && i!=1 && i!=16 && i!=17 && i!=15)
//                addTile(new Wall(i * 64, 300, 64, 64, true, Id.wall, this));
//        }
        clearLevel();
        int width = level.getWidth();
        int height = level.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = level.getRGB(x, y);

                //Shift bit value. RGB is 256 * 256 * 256 like a 2^24 number
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 0 && green == 0 && blue == 0)
                    addTile(new Wall(x * 64, y * 64, 64, 64, true, Id.wall, this));

                if (red == 0 && green == 0 && blue == 255) {
                    //    JOptionPane.showMessageDialog(null,"player");
                    player = new Player(x * 64, y * 64, 64, 64, false, Id.player1, this);
                    addEntity(player);
                    System.out.println(x*64);
                    System.out.println(y*64);
                }


                //no naked redMushroom
//                if (red == 255 && green == 0 && blue == 0) {
//                    //   JOptionPane.showMessageDialog(null,"RedMushroom");
//                    addEntity(new RedMushroom(x * 64, y * 64, 64, 64, Id.redMushroom, this));
//                }

                if (red == 50 && green == 50 && blue == 50) {
                    //   JOptionPane.showMessageDialog(null,"Goomba");
                    //   JOptionPane.showMessageDialog(null,"RedMushroom");
                    //addEntity(new Goomba(x * 64, y * 64, 64, 64, true, Id.goomba, this));
                    addEntity(new Koopa(x * 64, y * 64, 64, 64, Id.koopa, this, koopaTags++));
                }

                if (red == 40 && green == 10 && blue == 10) {
                    //   JOptionPane.showMessageDialog(null,"Goomba");
                    //   JOptionPane.showMessageDialog(null,"RedMushroom");
                    //addEntity(new Goomba(x * 64, y * 64, 64, 64, true, Id.goomba, this));
                    addEntity(new Goomba(x * 64, y * 64, 64, 64,true, Id.goomba, this, goombaTags++));
                }


                if (red == 70 && green == 20 && blue == 20) {
                    //   JOptionPane.showMessageDialog(null,"Goomba");
                    //   JOptionPane.showMessageDialog(null,"RedMushroom");
                    //addEntity(new Goomba(x * 64, y * 64, 64, 64, true, Id.goomba, this));
                    addEntity(new Hedgehog(x * 64, y * 64, 64, 64, Id.hedgehog, this, hedgeHogTags++));
                }





                if (red == 255 && green == 255 && blue == 0) {
                    addTile(new PowerUpBlock(x * 64, y * 64, 64, 64, true, Id.powerUp, this, Game.greenMushroom, "GREEN", 0));
                }

                //There's a doubt that if only x and y suffice
                if (red == 0 && green==100 && blue == 0) {
                    addTile(new Pipe(x * 64, y * 64, 64, 64, true, Id.pipe, this, 0, true, plantTags++));
                }

                if (red == 0 && green == 50&& blue == 0) {
                    addTile(new Pipe(x * 64, y * 64, 64, 64, true, Id.pipe, this, 0, false, 0));
                }

                if (red == 255 && green == 250 && blue == 0) {
                    addTile(new Coin(x * 64, y * 64, 64, 64, true, Id.coin, this, 0));
                }

                if (red == 220 && green == 60 && blue == 0) {
                    // addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this,Game.greenMushroom,"GREEN"));
                    addTile(new FireFlowerSpot(x * 64, y * 64, 64, 64, Id.fireFlowerSpot, this, 0));
                }

                if (red == 150 && green == 80 && blue == 0) {
                    addTile(new CastleBrick(x * 64, y * 64, 64, 64, true, Id.casteBrick, this));
                }

                if (red == 220 && green == 255 && blue == 255) {
                    addTile(new CastleDoor(x * 64, y * 64, 64, 64, true, Id.castleDoor, this));
                }


                if (red == 100 && green == 255 && blue == 255) {
                    //      JOptionPane.showMessageDialog(null,"castelDor");
                    addTile(new Prince(x * 64, y * 64, 64, 64, true, Id.prince, this));
                }

                if (red == 220 && green == 220 && blue == 220) {
                    addTile(new Hole(x * 64, y * 64, 64, 64, false, Id.hole, this, 0));
                    //       JOptionPane.showMessageDialog(null,"tichi");
                }

                if (red == 30 && green == 30 && blue == 30) {
                    addTile(new Stair(x * 64, y * 64, 64, 64, true, Id.stair, this));
                    //       JOptionPane.showMessageDialog(null,"tichi");
                }

                if (red == 100 && green == 80 && blue == 20) {
                    addTile(new Brick(x * 64, y * 64, 64, 64, true, Id.brick, this, 0));
                    //       JOptionPane.showMessageDialog(null,"tichi");
                }


            }
        }
    }

    public void addEntity(Entity e) {
        entity.add(e);
    }

    public void addTile(Tile t) {
        tile.add(t);
    }

    public void removeEntity(Entity e) {
        entity.remove(e);
    }

    public void removeTile(Tile t) {
        tile.remove(t);
    }

    public ArrayList<Entity> getEntity() {
        return entity;
    }

    public ArrayList<Tile> getTile() {
        return tile;
    }

    public void clearLevel() {
        entity=new ArrayList<>();
        tile=new ArrayList<>();
    }

    public static void clearAll() {
        changedKoopaTags = new ArrayList<>();
        otherPlayerFireBalls = new ArrayList<>();
        deadThings = new ArrayList<>();
        changedLiveKoopas = new ArrayList<>();
        addedRedMushroomY = new ArrayList<>();
        addedRedMushroomX = new ArrayList<>();
    }
}
