package gamestate;

import java.awt.*;
import java.awt.event.KeyEvent;

public interface StateMethods {
    public void update();
    public void draw(Graphics g);
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);
}
