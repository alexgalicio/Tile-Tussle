package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {
    public static final String PLAYER_ATLAS = "player_atlas.png";
    public static final String TILE1 = "tile01.png";
    public static final String TILE2 = "tile02.png";
    public static final String TILE8 = "tile03.png";
    public static final String SLIME = "slime.png";
    public static final String HEART_FULL = "heart_full.png";
    public static final String HEART_BLANK = "heart_blank.png";
    public static final String ARROW = "arrow.png";
    public static final String HEART = "heart.png";



    public static BufferedImage GetSpriteAtlas(String filename) {
        BufferedImage img = null;

        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}
