package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;
    public boolean up, down, left, right;
    private boolean enterPressed = false, spacePressed = false;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gamePanel.gameState == gamePanel.TITLE) {
            titleState(code);
        }
        if (gamePanel.gameState == gamePanel.PLAYING) {
            playState(code);
        }
        if (gamePanel.gameState == gamePanel.PAUSE) {
            optionState(code);
        }
        if (gamePanel.gameState == gamePanel.GAMEOVER) {
            gameOverState(code);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            up = false;
        }
        if (code == KeyEvent.VK_S) {
            down = false;
        }
        if (code == KeyEvent.VK_A) {
            left = false;
        }
        if (code == KeyEvent.VK_D) {
            right = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }

    }

    private void titleState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gamePanel.gameState = gamePanel.PLAYING;
            gamePanel.stopMusic();
            gamePanel.playMusic(5);
        }
    }

    private void playState(int code) {
        if (code == KeyEvent.VK_W)
            up = true;
        if (code == KeyEvent.VK_S)
            down = true;
        if (code == KeyEvent.VK_A)
            left = true;
        if (code == KeyEvent.VK_D)
            right = true;
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gamePanel.gameState = gamePanel.PAUSE;
        }
    }

    private void optionState(int code) {

        if (code == KeyEvent.VK_W) {
            gamePanel.ui.commandNum--;
            if (gamePanel.ui.commandNum < 0)
                gamePanel.ui.commandNum = 2;
        }

        if (code == KeyEvent.VK_S) {
            gamePanel.ui.commandNum++;
            if (gamePanel.ui.commandNum > 2)
                gamePanel.ui.commandNum = 0;
        }

        if (gamePanel.ui.subState == 0) {
            if (code == KeyEvent.VK_ENTER) {
                if (gamePanel.ui.commandNum == 0)
                    gamePanel.gameState = gamePanel.PLAYING;
                if (gamePanel.ui.commandNum == 1) {
                    gamePanel.ui.subState = 1;
                    gamePanel.ui.commandNum = 0;
                }
                if (gamePanel.ui.commandNum == 2) {
                    gamePanel.restart();
                    gamePanel.gameState = gamePanel.TITLE;
                    gamePanel.ui.commandNum = 0;
                }
            }
        } else if (gamePanel.ui.subState == 1) {
            if (code == KeyEvent.VK_A) {
                if (gamePanel.ui.commandNum == 0) {
                    if (gamePanel.music.volumeScale > 0) {
                        gamePanel.music.volumeScale--;
                        gamePanel.music.checkVolume();
                    }
                    if (gamePanel.se.volumeScale > 0) {
                        gamePanel.se.volumeScale--;
                    }
                }
            }
            if (code == KeyEvent.VK_D) {
                if (gamePanel.ui.commandNum == 0) {
                    if (gamePanel.music.volumeScale < 5) {
                        gamePanel.music.volumeScale++;
                        gamePanel.music.checkVolume();
                    }
                    if (gamePanel.se.volumeScale < 5) {
                        gamePanel.se.volumeScale++;
                    }
                }
            }

            if (code == KeyEvent.VK_ENTER) {
                if (gamePanel.ui.commandNum == 1) {
                    gamePanel.ui.subState = 2;
                    gamePanel.ui.commandNum = 0;
                }
                if (gamePanel.ui.commandNum == 2) {
                    gamePanel.ui.subState = 0;
                    gamePanel.ui.commandNum = 0;
                }
            }
        } else if (gamePanel.ui.subState == 2) {
            if (code == KeyEvent.VK_ENTER) {
                gamePanel.ui.subState = 0;
                gamePanel.ui.commandNum = 0;
            }

        }
    }

    private void gameOverState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gamePanel.stopMusic();
            gamePanel.playMusic(5);
            gamePanel.restart();
            gamePanel.gameState = gamePanel.PLAYING;
        }
    }

    public void setSpacePressed(boolean spacePressed) {
        this.spacePressed = spacePressed;
    }

    public boolean isSpacePressed() {
        return spacePressed;
    }

}
