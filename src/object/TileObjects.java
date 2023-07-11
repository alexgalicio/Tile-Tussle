package object;

import entities.Entity;
import main.GamePanel;
import utilz.LoadSave;

import java.awt.image.BufferedImage;

public class TileObjects extends Entity {
    private BufferedImage[] tileImg;

    public TileObjects(GamePanel gamePanel) {
        super(gamePanel);

        loadImg();
        collision = true;

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 24;
        solidArea.height = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    private void loadImg() {
        BufferedImage tileSprite = LoadSave.GetSpriteAtlas(LoadSave.TILE8);
        tileImg = new BufferedImage[4];

        for (int i = 0; i < tileImg.length; i++)
            tileImg[i] = tileSprite.getSubimage(8 * i, 0, 8, 8);

        sprite = tileImg;
    }


}
