package entities;

import main.GamePanel;
import utilz.LoadSave;

import java.awt.image.BufferedImage;

public class Enemy extends Entity {
    private BufferedImage[] enemyImg;

    public Enemy(GamePanel gamePanel) {
        super(gamePanel);

        loadImg();

        collision = true;
        direction = "down";
        speed = 1;

        solidArea.x = 6;
        solidArea.y = 16;
        solidArea.width = 18;
        solidArea.height = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }

    private void loadImg() {
        BufferedImage enemySprite = LoadSave.GetSpriteAtlas(LoadSave.SLIME);
        enemyImg = new BufferedImage[4];

        for (int i = 0; i < enemyImg.length; i++)
            enemyImg[i] = enemySprite.getSubimage(8 * i, 0, 8, 8);

        sprite = enemyImg;
    }

}
