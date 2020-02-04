package Mario.Input;

import GameEntity.Entity;
import GameEntity.FireBall;
import GameEntity.Player;
import GameTile.Tile;
import Mario.Game;
import Mario.Handler;
import Mario.Id;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    private boolean fire = false;
    public static boolean longJump, invincible, fullLives, infiniteBalls;
    private int jPressed, lPressed, iPressed, bPressed;

    @Override

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        for (int i = 0; i < Game.handler.getEntity().size(); i++) {
            Entity en = Game.getHandler().getEntity().get(i);

            if (en.getId() != Id.player1)
                continue;

//            if (en.getJumping())
//                continue;
            if (en.getGoingDownPipe() || Game.showDeathScreen || Game.paused)
                return;

            switch (key) {
                case KeyEvent.VK_C:
                    jPressed = 0;
                    lPressed = 0;
                    iPressed = 0;
                    bPressed = 0;
                    Game.coins++;
                    break;


                case KeyEvent.VK_P:
                    setCheatZero();

                    Game.myPaues = true;
                    break;

                case KeyEvent.VK_SPACE:
                    setCheatZero();

                    if (((Player) en).getStatus() != 2 || fire || Game.fireBalls == 0 || Player.liveFireBalls == 3)
                        return;

                    fire = true;
                    Game.fireBalls--;


                    if (en.getFacing() == 0)
                        Game.handler.addEntity((new FireBall(en.getX() + 24, en.getY() + 12, 24, 24, Id.fireBall, Game.handler, en.getFacing())));

                    else
                        Game.handler.addEntity((new FireBall(en.getX() - 24, en.getY() + 12, 24, 24, Id.fireBall, Game.handler, en.getFacing())));
                    break;


                case KeyEvent.VK_W:
                    setCheatZero();
                    Game.lastJumpX=en.getX();
                    Game.lastJumpY=en.getY();

//                    en.setVelY(-5);
                    if (!en.getJumping()) {
                        en.setJumping(true);

                        en.setGravity(11.5);

                        if (longJump)
                            en.setGravity(30);
                    }


                    break;

                case KeyEvent.VK_S:

                    setCheatZero();
                    ((Player) en).setSit(true);

                    for (int q = 0; q < Game.handler.getTile().size(); q++) {
                        Tile t = Game.getHandler().getTile().get(q);

                        if (t.getId() == Id.pipe) {
//                            System.out.println("mario : "+en.getBoundsBottom());
//                            System.out.println("pipe : "+t.getBounds());
                            //  System.out.println();
                            if (en.getBoundsBottom().intersects(t.getBounds())) {
                                //     JOptionPane.showMessageDialog(null,"intersect");
                                if (en.getGoingDownPipe() != true)
                                    en.setGoingDownPipe(true);

                            }
                        }
                    }

                    break;


//
//                case KeyEvent.VK_S:
//                    en.setVelY(5);
//                    break;


                case KeyEvent.VK_D:
                    setCheatZero();

//                    if(en.getJumping())
//                        return;


                    en.setVelX(5);
                    en.setFacing(1);
                    break;

                case KeyEvent.VK_A:
                    setCheatZero();
//                    if(en.getJumping())
//                        return;

                    en.setFacing(0);
                    en.setVelX(-5);
                    break;


                case KeyEvent.VK_J:
                    jPressed++;
                    iPressed = 0;
                    lPressed = 0;
                    bPressed = 0;


                    if (jPressed == 3) {
                        if (longJump == false)
                            longJump = true;

                        else
                            longJump = false;

                        jPressed = 0;
                        jPressed = 0;
                    }


                    break;

                case KeyEvent.VK_L:
                    jPressed = 0;
                    iPressed = 0;
                    lPressed++;
                    bPressed = 0;

                    if (lPressed == 3) {
                        Game.lives = 3;
                        lPressed = 0;
                    }
                    break;

                case KeyEvent.VK_I:
                    jPressed = 0;
                    iPressed++;
                    lPressed = 0;
                    bPressed = 0;

                    if (iPressed == 3)
                        invincible = true;

                    break;


                case KeyEvent.VK_B:
                    jPressed = 0;
                    iPressed = 0;
                    lPressed = 0;
                    bPressed++;

                    if (bPressed == 3)
                        infiniteBalls = true;

                    break;

            }


        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();


        for (Entity en : Game.getHandler().getEntity()) {
            if (en.getId() == Id.player1) {
//                if(en.getJumping())
//                    continue;

                switch (key) {


                    case KeyEvent.VK_W:
                        en.setVelY(0);
                        break;


                    case KeyEvent.VK_S:
                        en.setVelY(0);
                        ((Player) en).setSit(false);
                        break;


                    case KeyEvent.VK_D:
                        en.setVelX(0);
                        break;

                    case KeyEvent.VK_A:
                        en.setVelX(0);
                        break;

                    case KeyEvent.VK_SPACE:
                        fire = false;
                        break;


                }
            }


        }


    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private void setCheatZero() {
        jPressed = 0;
        lPressed = 0;
        iPressed = 0;
        bPressed = 0;
    }
}
