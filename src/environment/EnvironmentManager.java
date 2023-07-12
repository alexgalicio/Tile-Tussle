package environment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentManager {
    private GamePanel gamePanel;
    private Lightning lightning;

    public EnvironmentManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setUp() {
        lightning = new Lightning(gamePanel, 200);
    }

    public void update() {
        lightning.update();
    }
    public void draw(Graphics g) {
        lightning.draw((Graphics2D) g);
    }
}
