package main;

import entities.Entity;
import object.Heart;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    private GamePanel gamePanel;
    private BufferedImage heartFull, heartBlank;
    private Graphics g;
    private Font heading, body;
    public int commandNum = 0;
    public int pauseScreenState = 0;
    private boolean showText;
    public int subState = 0;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        InputStream is = getClass().getResourceAsStream("/font/antiquity-print.ttf");

        try {
            heading = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/pansyhand.ttf");
            body = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        Entity heart = new Heart(gamePanel);
        heartFull = heart.img;
        heartBlank = heart.img2;
    }

    private void continueScreen(BufferedImage img) {
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 5);
        int height = (int) (gamePanel.tileSize * 3.5);
        int x = gamePanel.screenWidth / 2 - (width / 2);
        int y = gamePanel.screenHeight / 2 - (height / 2);
        drawSubWindow(x, y, width, height);

        String text = "CONTINUE";
        x = getXForCenteredText(text);
        y = gamePanel.tileSize * 6;
        g.drawString(text, x, y);
        if (commandNum == 0)
            g.drawImage(img, x - gamePanel.tileSize, y - gamePanel.tileSize + 8, 28, 28, null);

        text = "OPTIONS";
        y = (int) (gamePanel.tileSize * 6.8);
        g.drawString(text, x, y);
        if (commandNum == 1)
            g.drawImage(img, x - gamePanel.tileSize, y - gamePanel.tileSize + 8, 28, 28, null);

        text = "EXIT";
        y = (int) (gamePanel.tileSize * 7.6);
        g.drawString(text, x, y);
        if (commandNum == 2)
            g.drawImage(img, x - gamePanel.tileSize, y - gamePanel.tileSize + 8, 28, 28, null);

    }

    private void menuScreen(BufferedImage img) {
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = (int) (gamePanel.tileSize * 3.5);
        int x = gamePanel.screenWidth / 2 - (width / 2);
        int y = gamePanel.screenHeight / 2 - (height / 2);
        drawSubWindow(x, y, width, height);

        String text = "VOLUME";
        x = getXForCenteredText(text) - 60;
        y = (int) (gamePanel.tileSize * 6);
        if (gamePanel.ui.commandNum == 0)
            g.drawImage(img, x - gamePanel.tileSize, y - gamePanel.tileSize + 8, 28, 28, null);
        g.drawRect(x+120, y-15, 100, 10);
        int volumeWidth = 20 * gamePanel.music.volumeScale;
        g.fillRect(x+120, y-15, volumeWidth, 10);
        int seWidth = 20 * gamePanel.se.volumeScale;
        g.fillRect(x+120, y-15, seWidth, 10);

        g.drawString(text, x, y);
        text = "CONTROLS";
        y = (int) (gamePanel.tileSize * 6.8);
        g.drawString(text, x, y);
        if (gamePanel.ui.commandNum == 1)
            g.drawImage(img, x - gamePanel.tileSize, y - gamePanel.tileSize + 8, 28, 28, null);

        text = "BACK";
        y = (int) (gamePanel.tileSize * 7.6);
        g.drawString(text, x, y);
        if (gamePanel.ui.commandNum == 2)
            g.drawImage(img, x - gamePanel.tileSize, y - gamePanel.tileSize + 8, 28, 28, null);

    }

    private void controlScreen() {
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 5);
        int height = (int) (gamePanel.tileSize * 3.5);
        int x = gamePanel.screenWidth / 2 - (width / 2);
        int y = gamePanel.screenHeight / 2 - (height / 2);
        drawSubWindow(x, y, width, height);

        String text = "MOVE: WASD";
        x = getXForCenteredText(text);
        y = gamePanel.tileSize * 6;
        g.drawString(text, x, y);

        text = "CLEAR: SPACE";
        y = (int) (gamePanel.tileSize * 6.8);
        g.drawString(text, x, y);

        text = "PAUSE: P";
        y = (int) (gamePanel.tileSize * 7.6);
        g.drawString(text, x, y);
    }

    public void drawOptionScreen() {
        g.setFont(body);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 50F));

        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.ARROW);

        switch (subState) {
            case 0 -> continueScreen(img);
            case 1 -> menuScreen(img);
            case 2 -> controlScreen();
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        g.setColor(new Color(0, 0, 0, 220));
        g.fillRect(x, y, width, height);
        g.setColor(new Color(255, 241, 232));

        ((Graphics2D) g).setStroke(new BasicStroke(4));
        g.drawRect(x + 6, y + 6, width - 12, height - 12);
    }

    public void drawTitleScreen() {
        g.setColor(new Color(171, 82, 54));
        g.setFont(heading);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 30F));

        String line1 = "TILE";
        String line2 = "TUSSLE";

        int x = getXForCenteredText(line1);
        int y = (int) (gamePanel.tileSize * 4.5);
        g.drawString(line1, x, y);

        x = getXForCenteredText(line2);
        y += g.getFontMetrics().getHeight();
        g.drawString(line2, x, y);

        showText = (System.currentTimeMillis() / 500) % 2 == 0;

        if (showText) {
            g.setFont(body);
            g.setFont(g.getFont().deriveFont(Font.PLAIN, 50F));
            String text = "PRESS ENTER TO START";
            x = getXForCenteredText(text);
            y += gamePanel.tileSize * 4;
            g.drawString(text, x, y);
        }
    }


    public int getXForCenteredText(String text) {
        int length = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }

    public void draw(Graphics g) {
        this.g = g;
        g.setColor(Color.white);

        if (gamePanel.gameState == gamePanel.TITLE)
            drawTitleScreen();

        if (gamePanel.gameState == gamePanel.PLAYING) {
            drawPlayerLife();
        }

        if (gamePanel.gameState == gamePanel.PAUSE) {
            drawOptionScreen();
        }

        if (gamePanel.gameState == gamePanel.GAMEOVER) {
            drawGameOverScreen();
        }

    }

    private void drawGameOverScreen() {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        g.setColor(Color.BLACK);
        g.setFont(body);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 70F));

        String text = "G A M E O V E R";
        int x = getXForCenteredText(text);
        int y = gamePanel.tileSize * 4;
        g.drawString(text, x, y);
        g.setColor(new Color(255, 241, 232));
        g.drawString(text, x - 3, y - 3);

        g.setColor(new Color(0, 0, 0, 150));
        text = "SCORE: " + gamePanel.player.score;
        x = getXForCenteredText(text);
        y += gamePanel.tileSize * 1.5;
        g.drawString(text, x, y);
        g.setColor(new Color(255, 241, 232));
        g.drawString(text, x - 3, y - 3);

        showText = (System.currentTimeMillis() / 500) % 2 == 0;

        if (showText) {
            g.setColor(new Color(0, 0, 0, 150));
            g.setFont(g.getFont().deriveFont(50F));
            text = "PRESS ENTER TO TRY AGAIN";
            x = getXForCenteredText(text);
            y += gamePanel.tileSize * 4;
            g.drawString(text, x, y);
            g.setColor(new Color(255, 241, 232));
            g.drawString(text, x - 3, y - 3);
        }

    }

    private void drawPlayerLife() {
        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;

        for (int i = 0; i < gamePanel.player.maxLife; i++) {
            g.drawImage(heartBlank, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            x += gamePanel.tileSize;
        }

        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;
        for (int i = 0; i < gamePanel.player.life; i++) {
            g.drawImage(heartFull, x, y, gamePanel.tileSize, gamePanel.tileSize, null);
            x += gamePanel.tileSize;
        }

    }

}
