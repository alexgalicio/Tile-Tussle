package entities;

import main.AssetSetter;
import main.GamePanel;
import main.KeyboardInputs;
import object.Heart;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity {
    private KeyboardInputs keyboardInputs;
    private BufferedImage animations[][];
    private int playerAction = IDLE;
    private boolean moving = false;
    private AssetSetter assetSetter;
    public final int screenX;
    public final int screenY;

    public Player(GamePanel gamePanel, KeyboardInputs keyboardInputs) {
        super(gamePanel);

        this.keyboardInputs = keyboardInputs;
        assetSetter = new AssetSetter(gamePanel);

        screenX = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);

        solidArea.x = 4;
        solidArea.y = 4;
        solidArea.width = 24;
        solidArea.height = 28;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        loadAnimations();
    }

    public void setDefaultValues() {
        x = gamePanel.tileSize * 7;
        y = gamePanel.tileSize * 7;
        speed = 3;
        direction = "right";
        maxLife = 4;
        life = maxLife;
        score = 0;
        invincible = false;
        invincibleCounter = 0;
    }

    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[3][5];
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 8, j * 8, 8, 8);
            }
        }
    }

    private void updateAniTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
            }
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    private void setAnimations() {
        int startAni = playerAction;

        if (moving)
            playerAction = WALK;
        else
            playerAction = IDLE;

        if (startAni != playerAction) {
            resetAniTick();
        }
    }

    private void updatePos() {
        moving = false;

        if (keyboardInputs.up) {
            direction = "up";
            moving = true;
        } else if (keyboardInputs.down) {
            direction = "down";
            moving = true;
        }

        if (keyboardInputs.left) {
            direction = "left";
            moving = true;
        } else if (keyboardInputs.right) {
            direction = "right";
            moving = true;
        }

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);

        int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
        pickUpObject(objectIndex);

//        int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.enemy);
//        contactMonster(monsterIndex);

        if (!collisionOn) {
            if (keyboardInputs.left || keyboardInputs.right || keyboardInputs.up || keyboardInputs.down) {
                switch (direction) {
                    case "up" -> y -= speed;
                    case "down" -> y += speed;
                    case "left" -> x -= speed;
                    case "right" -> x += speed;
                }
            }
        }
    }

    private boolean allObjectsCollected() {
        for (int i =0; i < gamePanel.obj.length; i++) {
            if (gamePanel.obj[i] != null && i != 3) {
                return false;
            }
        }
        return true;
    }

    private void respawnAndShake() {
        gamePanel.shaking = true;
        gamePanel.shakeTimer = 0;
        gamePanel.shakeX = 0;
        gamePanel.shakeY = 0;

        assetSetter.setObject();
        assetSetter.setEnemy();
    }

//    private void contactMonster(int monsterIndex) {
//        if (monsterIndex != 999) {
//            if (!invincible) {
//                gamePanel.playSE(4);
//                life -= 1;
//                invincible = true;
//            }
//        }
//    }

    private void pickUpObject(int objectIndex) {
        if (objectIndex != 999) {
            if (gamePanel.obj[objectIndex] instanceof Heart) {
                gamePanel.playSE(2);
                gamePanel.obj[objectIndex] = null;
                life += 1;
            } else {
                gamePanel.playSE(2);
                gamePanel.obj[objectIndex] = null;
            }
        }
    }

    public void update() {
        if (life <= 0) {
            if (playerAction != DEAD) {
                gamePanel.playSE(3);
                gamePanel.shaking = true;
                playerAction = DEAD;
                resetAniTick();
            } else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= aniIndex - 1)
                gamePanel.gameState = gamePanel.GAMEOVER;
            else
                updateAniTick();

            return;
        }

        updatePos();
        updateAniTick();
        setAnimations();

        if (keyboardInputs.isSpacePressed() && allObjectsCollected()) {
            if (!gamePanel.shaking) {
                gamePanel.playSE(1);
                respawnAndShake();
                keyboardInputs.setSpacePressed(false);
                score++;
            }
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void draw(Graphics g) {
        if (invincible && life > 0)
            ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));

        g.drawImage(animations[playerAction][aniIndex], screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

        g.setColor(Color.RED);
        g.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

        g.drawString("Invincible: " + invincibleCounter, screenX, screenY);

    }
}
