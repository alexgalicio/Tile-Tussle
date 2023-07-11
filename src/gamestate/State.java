package gamestate;

import main.GamePanel;

public class State {
    protected GamePanel gamePanel;
    public State(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
