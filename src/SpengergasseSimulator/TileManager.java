package SpengergasseSimulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    public Tile[] tiles;
    public GamePanel gp;
    public int tileCount;
    public int[][] mapTileNum;

    public TileManager(int tileCount, GamePanel gp) {
        this.gp = gp;
        this.tiles = new Tile[tileCount];
        this.tileCount = tileCount;
        this.mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
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

                tiles[0] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/grass00.png"))), false);
                tiles[1] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/grass01.png"))), false);
                tiles[2] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/wall.png"))), false);
                tiles[3] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/road00.png"))), false);
                tiles[4] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/water00.png"))), false);
            }
            else {
                System.err.println("From or To bigger than count of tiles, NO LOADING POSSIBLE");
            }
        }catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void loadMap(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);

            assert is != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(is));


            int col = 0, row = 0;

            while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
                String line = br.readLine();

                while (col < gp.maxScreenCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void drawFromMap(Graphics2D g2) {
        int col = 0, row = 0;
        int x = 0, y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
            int tileNum = mapTileNum[col][row];

            g2.drawImage(tiles[tileNum].img, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;

            if (col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
    public void draw(Graphics2D g2, int which, int x, int y) {
        g2.drawImage(tiles[which].img, x, y, gp.tileSize, gp.tileSize, null);
    }
}
