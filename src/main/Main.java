package main;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();

        GamePanel gamePanel = new GamePanel();
        jFrame.add(gamePanel);

        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         jFrame.setLocationRelativeTo(null);
        jFrame.pack();

        jFrame.setVisible(true);
        gamePanel.setUpGame();

    }
}