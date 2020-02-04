package GameGFX;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;

public class SpriteSheet {
    private BufferedImage sheet;

    public SpriteSheet(String path)
    {
        try {
          //  sheet = ImageIO.read(getClass().getResource(path));
            sheet = ImageIO.read(new File("C:\\Users\\erfan\\Desktop\\dummy\\res\\spritesheet.png"));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(int x,int y)
    {
        int u=x,v=y;
        if(x==0)
            u=1;

        if(y==0)
            v=1;

        return sheet.getSubimage(u*32-32,v*32-32,32,32);
    }
}
