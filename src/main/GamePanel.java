package main;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import environment.EnvironmentManager;
import gamestate.GameState;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class GamePanel extends JPanel implements Runnable {

    // window
    public int originalTileSize = 8;
    public int scale = 4;
    public int tileSize = originalTileSize * scale;
    public int screenCol = 13;
    public int screenRow = 13;
    public int screenWidth = tileSize * screenCol;
    public int screenHeight = tileSize * screenRow;

    // world map
    public final int worldCol = 15;
    public final int worldRow = 15;

    private KeyboardInputs keyboardInputs;
    public Player player;
    public TileManager tileManager;
    public CollisionChecker collisionChecker;
    public AssetSetter assetSetter;
    public UI ui;
    public Entity[] obj;
    public Entity[] enemy;
    ArrayList<Entity> entities = new ArrayList<>();
    public Sound se, music;
    private EnvironmentManager em;

    // game state
    public int gameState;
    public final int TITLE = 0;
    public final int PLAYING = 1;
    public final int PAUSE = 2;
    public final int GAMEOVER = 3;

    // shake screen
    public boolean shaking = false;
    private int shakeMagnitude = 5;
    private int shakeDuration = 20;
    public int shakeTimer = 0;
    public int shakeX = 0;
    public int shakeY = 0;


    public GamePanel() {
        setWindowSize();
        Color color = new Color(255, 241, 232);
        setBackground(color);

        initClasses();

        setFocusable(true);
        requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        keyboardInputs = new KeyboardInputs(this);
        addKeyListener(keyboardInputs);

        player = new Player(this, keyboardInputs);
        tileManager = new TileManager(this);
        collisionChecker = new CollisionChecker(this);
        obj = new Entity[10];
        enemy = new Entity[10];
        assetSetter = new AssetSetter(this);
        ui = new UI(this);
        se = new Sound();
        music = new Sound();
        em = new EnvironmentManager(this);
    }

    private void setWindowSize() {
        Dimension size = new Dimension(screenWidth, screenHeight);
        setPreferredSize(size);
    }

    public void setUpGame() {
        assetSetter.setObject();
        assetSetter.setEnemy();
        gameState = TITLE;
        playMusic(0);

        em.setUp();
    }

    public void restart() {
        player.setDefaultValues();
        assetSetter.setObject();
        assetSetter.setEnemy();
        em.setUp();
    }

    public void update() {
        if (gameState == PLAYING) {
            player.update();

            for (Entity entity : obj) {
                if (entity != null)
                    entity.update();
            }

            for (Entity entity : enemy) {
                if (entity != null)
                    entity.update();
            }

            if (shaking) {
                shakeTimer++;
                if (shakeTimer <= shakeDuration) {
                    shakeX = (int) (Math.random() * shakeMagnitude * 2) - shakeMagnitude;
                    shakeY = (int) (Math.random() * shakeMagnitude * 2) - shakeMagnitude;
                } else {
                    shaking = false;
                    shakeX = 0;
                    shakeY = 0;
                }
            }

            em.update();
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState == TITLE) {
            ui.draw(g);
        } else {
            g.translate(shakeX, shakeY);

            tileManager.draw(g);
            entities.add(player);


            for (Entity entity : obj) {
                if (entity != null)
                    entities.add(entity);
            }

            for (Entity entity : enemy) {
                if (entity != null)
                    entities.add(entity);
            }

            entities.sort((o1, o2) -> Integer.compare(o1.x, o2.y));

            for (Entity entity : entities) {
                entity.draw(g);
            }

            entities.clear();
            em.draw(g);
            ui.draw(g);
        }

        g.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    private void startGameLoop() {
        Thread gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        long FPS_SET = 60;
        double timePerFrame = 1000000000.0 / FPS_SET;

        double delta = 0;
        long lastFrame = System.nanoTime();
        long now;

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {
            now = System.nanoTime();
            delta += (now - lastFrame) / timePerFrame;
            lastFrame = now;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                frames++;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }

}
