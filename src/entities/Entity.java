package entities;

import main.GamePanel;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;


public abstract class Entity {
    protected GamePanel gamePanel;
    public int x, y;
    public int speed;
    public String direction;
    public boolean collision = false;
    public boolean collisionOn = false;
    public Rectangle solidArea = new Rectangle(0, 0, 32, 32);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public BufferedImage img, img2 = LoadSave.GetSpriteAtlas(LoadSave.HEART_FULL);
    public int score;

    public int life;
    public int maxLife;
    public boolean invincible = false;
    public int invincibleCounter = 0;

//
    public BufferedImage[] sprite;
    public int aniTick, aniIndex, aniSpeed = 8;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void update() {
        updateAnimationTick();
        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.enemy);
        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if (contactPlayer) {
            if (!gamePanel.player.invincible) {
                gamePanel.playSE(4);
                gamePanel.player.life -=1;
                gamePanel.player.invincible = true;
            }
        }

        if (!collisionOn) {
            switch (direction) {
                case "up" -> y -= speed;
                case "down" -> y += speed;
                case "left" -> x -= speed;
                case "right" -> x += speed;
            }
        }
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= sprite.length) {
                aniIndex = 0;
            }
        }
    }

    private void setAction() {
        Player player = gamePanel.player;

        int dx = player.x - x;
        int dy = player.y - y;

        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                direction = "right";
            } else {
                direction = "left";
            }
        } else {
            if (dy > 0) {
                direction = "down";
            } else {
                direction = "up";
            }
        }
    }

    public void draw(Graphics g) {
        int screenX = x - gamePanel.player.x + gamePanel.player.screenX;
        int screenY = y - gamePanel.player.y + gamePanel.player.screenY;

        g.drawImage(sprite[aniIndex], screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

        g.setColor(Color.RED);
        g.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
    }


}
