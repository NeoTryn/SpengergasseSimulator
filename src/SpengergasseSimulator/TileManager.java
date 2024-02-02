package SpengergasseSimulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TileManager {
    public Tile[] tiles;
    public GamePanel gp;
    public int tileCount;

    public TileManager(int tileCount, GamePanel gp) {
        this.gp = gp;
        this.tiles = new Tile[tileCount];
        this.tileCount = tileCount;
    }

    public void loadTiles(int from, int to) {
        try {

            if (to < this.tileCount && from < this.tileCount) {
                // Unnecessarily loading in the earth tile 10 times. Can be used with some string manipulation and the numbered tiles, though
               /*for (int i = from; i <= to; i++) {
                    Tile temp = new Tile();
                    temp.img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/earth.png")));
                    tiles[i] = temp;
                }*/

                tiles[0] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/grass01.png"))), false);

            }
            else {
                System.err.println("From or To bigger than count of tiles, NO LOADING POSSIBLE");
            }
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void draw(Graphics2D g2, int which, int x, int y) {
        g2.drawImage(tiles[which].img, x, y, gp.tileSize, gp.tileSize, null);
    }
}
