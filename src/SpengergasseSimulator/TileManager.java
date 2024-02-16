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
        this.mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
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

                tiles[1] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/wall.png"))), false);
                tiles[1].collision = true;

                tiles[2] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/water00.png"))), false);
                tiles[2].collision = true;

                tiles[3] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/earth.png"))), false);

                tiles[4] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/tree.png"))), false);
                //tiles[4].collision = true;

                tiles[5] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/earth.png"))), false);
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

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void drawFromMap(Graphics2D g2, int sideNum, boolean fading) {
        int worldCol = 0, worldRow = 0;

        int playerCol = gp.player.worldX / gp.tileSize;
        int playerRow = gp.player.worldY / gp.tileSize;
        int restRightCol = gp.maxWorldCol - playerCol;
        int restDownRow = gp.maxWorldRow - playerRow;
        int rightColsLeftToDraw = (gp.maxScreenCol / 2) - restRightCol;
        int upRowsLeftToDraw = (gp.maxScreenRow / 2) - playerRow;
        int leftColsLeftToDraw = (gp.maxScreenCol / 2) - playerCol;
        int downRowsLeftToDraw = (gp.maxScreenRow / 2) - restDownRow;

        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //int rightScreenX = gp.worldWidth - gp.tileSize - (int) gp.screenWidth, rightScreenY = gp.worldHeight - gp.tileSize - (int) gp.screenHeight;

            if (worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                    worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                    worldY < gp.player.worldY + gp.player.screenY + gp.tileSize &&
                    worldY > gp.player.worldY - gp.player.screenY - gp.tileSize) {

                g2.drawImage(tiles[tileNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }

        if (gp.player.worldX + gp.player.screenX >= gp.worldWidth - gp.tileSize) {
            drawRightSide(g2, 4, false, rightColsLeftToDraw + 2);
        }

        if (gp.player.worldX - gp.player.screenX <= gp.tileSize) {
            drawLeftSide(g2, 4, false, leftColsLeftToDraw + 2);
        }

        if (gp.player.worldY - gp.player.screenY <= gp.tileSize) {
            drawUpSide(g2, 4, false, upRowsLeftToDraw + 2);
        }

        if (gp.player.worldY + gp.player.screenY >= gp.worldHeight - gp.tileSize) {
            drawDownSide(g2, 4, false, downRowsLeftToDraw + 2);
        }

        if (gp.player.worldX - gp.player.screenX <= gp.tileSize && gp.player.worldY - gp.player.screenY <= gp.tileSize) {
            drawUpperLeftCorner(g2, 4, false, leftColsLeftToDraw + 2, upRowsLeftToDraw + 2);
        }

        if (gp.player.worldX - gp.player.screenX <= gp.tileSize && gp.player.worldY + gp.player.screenY >= gp.worldHeight - gp.tileSize) {
            drawLowerLeftCorner(g2, 4, false, leftColsLeftToDraw + 2, downRowsLeftToDraw + 2);
        }

        if (gp.player.worldX + gp.player.screenX >= gp.worldWidth - gp.tileSize && gp.player.worldY - gp.player.screenY <= gp.tileSize) {
            drawUpperRightCorner(g2, 4, false, rightColsLeftToDraw + 2, upRowsLeftToDraw + 2);
        }

        if (gp.player.worldY + gp.player.screenY >= gp.worldHeight - gp.tileSize && gp.player.worldX + gp.player.screenX >= gp.worldWidth - gp.tileSize) {
            drawLowerRightCorner(g2, 4, false, rightColsLeftToDraw + 2, downRowsLeftToDraw + 2);
        }
    }

    private void drawRightSide(Graphics2D g2, int sideNum, boolean fading, int rightScreenCols) {

        int col = gp.maxWorldCol, row = 0;

        while (col < rightScreenCols + gp.maxWorldCol && row < gp.maxWorldRow) {

            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                    worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                    worldY < gp.player.worldY + gp.player.screenY + gp.tileSize &&
                    worldY > gp.player.worldY - gp.player.screenY - gp.tileSize) {

                g2.drawImage(tiles[sideNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col++;

            if (col == rightScreenCols + gp.maxWorldCol) {
                col = gp.maxWorldCol;
                row++;
            }
        }
    }

    private void drawLeftSide(Graphics2D g2, int sideNum, boolean fading, int leftScreenCols) {

        int col = 0, row = 0;
        leftScreenCols = -leftScreenCols;

        while (col > leftScreenCols && row < gp.maxWorldRow) {

            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                    worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                    worldY < gp.player.worldY + gp.player.screenY + gp.tileSize &&
                    worldY > gp.player.worldY - gp.player.screenY - gp.tileSize) {

                g2.drawImage(tiles[sideNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col--;

            if (col == leftScreenCols) {
                col = 0;
                row++;
            }
        }
    }

    private void drawUpSide(Graphics2D g2, int sideNum, boolean fading, int upScreenRows) {

        int col = 0, row = 0;
        upScreenRows = -upScreenRows;

        while (col < gp.maxWorldCol && row > upScreenRows) {

            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                    worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                    worldY < gp.player.worldY + gp.player.screenY + gp.tileSize &&
                    worldY > gp.player.worldY - gp.player.screenY - gp.tileSize) {

                g2.drawImage(tiles[sideNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row--;
            }
        }
    }

    private void drawDownSide(Graphics2D g2, int sideNum, boolean fading, int downScreenCols) {

        int col = 0, row = gp.maxWorldRow;

        while (col < gp.maxWorldCol && row < gp.maxWorldRow + downScreenCols) {

            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                    worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                    worldY < gp.player.worldY + gp.player.screenY + gp.tileSize &&
                    worldY > gp.player.worldY - gp.player.screenY - gp.tileSize) {

                g2.drawImage(tiles[sideNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    private void drawUpperLeftCorner(Graphics2D g2, int sideNum, boolean fading, int leftCols, int upRows) {
        int col = 0, row = 0;
        upRows = -upRows;
        leftCols = -leftCols;

        while (col > leftCols && row > upRows) {
            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                    worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                    worldY < gp.player.worldY + gp.player.screenY + gp.tileSize &&
                    worldY > gp.player.worldY - gp.player.screenY - gp.tileSize) {

                g2.drawImage(tiles[sideNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col--;

            if (col == leftCols) {
                col = 0;
                row --;
            }
        }
    }

    private void drawLowerLeftCorner(Graphics2D g2, int sideNum, boolean fading, int leftCols, int downRows) {
        int col = 0, row = gp.maxWorldRow;
        leftCols = -leftCols;

        while (col > leftCols && row < downRows + gp.maxWorldRow) {
            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                    worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                    worldY < gp.player.worldY + gp.player.screenY + gp.tileSize &&
                    worldY > gp.player.worldY - gp.player.screenY - gp.tileSize) {

                g2.drawImage(tiles[sideNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col--;

            if (col == leftCols) {
                col = 0;
                row++;
            }
        }
    }

    private void drawLowerRightCorner(Graphics2D g2, int sideNum, boolean fading, int rightCols, int downRows) {
        int col = gp.maxWorldCol, row = gp.maxWorldRow;

        while (col < rightCols + gp.maxWorldCol && row < downRows + gp.maxWorldRow) {
            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                    worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                    worldY < gp.player.worldY + gp.player.screenY + gp.tileSize &&
                    worldY > gp.player.worldY - gp.player.screenY - gp.tileSize) {

                g2.drawImage(tiles[sideNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col++;

            if (col == gp.maxWorldRow + rightCols) {
                col = gp.maxWorldCol;
                row++;
            }
        }
    }

    private void drawUpperRightCorner(Graphics2D g2, int sideNum, boolean fading, int rightCols, int upRows) {
        int col = gp.maxWorldCol, row = 0;
        upRows = -upRows;

        while (col < rightCols + gp.maxWorldCol && row > upRows) {
            int worldX = col * gp.tileSize;
            int worldY = row * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                    worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                    worldY < gp.player.worldY + gp.player.screenY + gp.tileSize &&
                    worldY > gp.player.worldY - gp.player.screenY - gp.tileSize) {

                g2.drawImage(tiles[sideNum].img, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            col++;

            if (col == gp.maxWorldRow + rightCols) {
                col = gp.maxWorldCol;
                row--;
            }
        }
    }

    public void draw(Graphics2D g2, int which, int x, int y) {
        g2.drawImage(tiles[which].img, x, y, gp.tileSize, gp.tileSize, null);
    }
}
