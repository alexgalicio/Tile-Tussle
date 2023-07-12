package main;

import entities.Enemy;
import object.Heart;
import object.TileObjects;

import java.util.Random;

public class AssetSetter {
    private GamePanel gamePanel;
    private Random random;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        random = new Random();
    }

    public void setObject() {
        for (int i = 0; i < 3; i++) {

            TileObjects tileObject = new TileObjects(gamePanel);

            int column = random.nextInt(10) + 3;
            int row = random.nextInt(10) + 3;

            tileObject.x = column * gamePanel.tileSize;
            tileObject.y = row * gamePanel.tileSize;
            gamePanel.obj[i] = tileObject;
        }

        if (gamePanel.player.life < 3) {
            Heart heart = new Heart(gamePanel);
            int column = random.nextInt(10) + 3;
            int row = random.nextInt(10) + 3;

            heart.x = column * gamePanel.tileSize;
            heart.y = row * gamePanel.tileSize;

            gamePanel.obj[3] = heart;
        }

    }

    public void setEnemy() {
        for (int i = 0; i < 2; i++) {

            Enemy enemy = new Enemy(gamePanel);

            int column = random.nextInt(10) + 3;
            int row = random.nextInt(10) + 3;

            enemy.x = column * gamePanel.tileSize;
            enemy.y = row * gamePanel.tileSize;
            gamePanel.enemy[i] = enemy;
        }
    }
}
