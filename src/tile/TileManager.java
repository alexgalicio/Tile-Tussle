package tile;

import main.GamePanel;
import utilz.LoadSave;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    private GamePanel gamePanel;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[5];
        mapTileNum = new int[gamePanel.worldCol][gamePanel.worldRow];

        getTileImg();
        loadMap();
    }

    public void getTileImg() {
        tile[0] = new Tile();
        tile[0].img = LoadSave.GetSpriteAtlas(LoadSave.TILE1);
        tile[0].collision = false;

        tile[1] = new Tile();
        tile[1].img = LoadSave.GetSpriteAtlas(LoadSave.TILE2);
        tile[1].collision = true;
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/map01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0, row = 0;
            while (col < gamePanel.worldCol && row < gamePanel.worldRow) {
                String line = br.readLine();
                while (col < gamePanel.worldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gamePanel.worldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        int col = 0;
        int row = 0;

        while (col < gamePanel.worldCol && row < gamePanel.worldRow) {
            int tileNum = mapTileNum[col][row];

            int x = col * gamePanel.tileSize;
            int y = row * gamePanel.tileSize;
            int screenX = x - gamePanel.player.x + gamePanel.player.screenX;
            int screenY = y - gamePanel.player.y + gamePanel.player.screenY;

            g.drawImage(tile[tileNum].img, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);

            col++;
            if (col == gamePanel.worldCol) {
                col = 0;
                row++;
            }
        }

    }
}
