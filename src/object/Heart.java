package object;

import entities.Entity;
import main.GamePanel;
import utilz.LoadSave;

import java.awt.image.BufferedImage;

public class Heart extends Entity {
    private BufferedImage[] heartImg;

    public Heart(GamePanel gamePanel) {
        super(gamePanel);

        img = LoadSave.GetSpriteAtlas(LoadSave.HEART_FULL);
        img2 = LoadSave.GetSpriteAtlas(LoadSave.HEART_BLANK);
        loadImg();

        collisionOn = true;

        solidArea.x = 12;
        solidArea.y = 12;
        solidArea.width = 12;
        solidArea.height = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    public void loadImg() {
        BufferedImage heartSprite = LoadSave.GetSpriteAtlas(LoadSave.HEART);
        heartImg = new BufferedImage[6];

        for (int i = 0; i < heartImg.length; i++)
            heartImg[i] = heartSprite.getSubimage(8 * i, 0, 8, 8);

        sprite = heartImg;
    }
}
