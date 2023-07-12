package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lightning {
    private GamePanel gamePanel;
    BufferedImage darkness;
    int dayCounter;
    float filterAlpha = 0F;
    final int day = 0;
    final int dusk = 1;
    final int night = 2;
    final int dawn = 3;
    int dayState = day;

    public Lightning(GamePanel gamePanel, int circleSize) {
        this.gamePanel = gamePanel;
        darkness = new BufferedImage(gamePanel.screenWidth, gamePanel.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) darkness.getGraphics();

        int centerX = gamePanel.player.screenX + (gamePanel.tileSize / 2);
        int centerY = gamePanel.player.screenY + (gamePanel.tileSize / 2);

        Color[] color = new Color[5];
        float[] fraction = new float[5];

        color[0] = new Color(0, 0, 0, 0F);
        color[1] = new Color(0, 0, 0, 0.25F);
        color[2] = new Color(0, 0, 0, 0.5F);
        color[3] = new Color(0, 0, 0, 0.75F);
        color[4] = new Color(0, 0, 0, 0.98F);

        fraction[0] = 0F;
        fraction[1] = 0.25F;
        fraction[2] = 0.5F;
        fraction[3] = 0.75F;
        fraction[4] = 1F;

        RadialGradientPaint gradientPaint = new RadialGradientPaint(centerX, centerY,
                circleSize / 2, fraction, color);

        g.setPaint(gradientPaint);
        g.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);
        g.dispose();
    }

    public void setLightSource() {

    }

    public void update() {
        if (dayState == day) {
            dayCounter++;
            if (dayCounter > 900) { // 600 = 10
                dayState = dusk;
                dayCounter = 0;
            }
        }

        if (dayState == dusk) {
            filterAlpha += 0.001F;
            if (filterAlpha > 1F) {
                filterAlpha = 1F;
                dayState = night;
            }
        }

        if (dayState == night) {
            dayCounter++;
            if (dayCounter > 900) {
                dayState = dawn;
                dayCounter = 0;
            }
        }

        if (dayState == dawn) {
            filterAlpha -= 0.01F;
            if(filterAlpha < 0F) {
                filterAlpha = 0;
                dayState = day;
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2.drawImage(darkness, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }
}
