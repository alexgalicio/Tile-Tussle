package object;

import entities.Entity;
import main.GamePanel;
import utilz.LoadSave;

public class Heart extends Entity {
    public Heart(GamePanel gamePanel) {
        super(gamePanel);

        img = LoadSave.GetSpriteAtlas(LoadSave.HEART_FULL);
        img2 = LoadSave.GetSpriteAtlas(LoadSave.HEART_BLANK);
    }
}
