package SpengergasseSimulator;

import java.util.Locale;

public class CollisionHandler {
    GamePanel gp;

    public CollisionHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity e) {
        if (e instanceof Player) {
            int leftWorldX = e.worldX + e.getHitBox().x;
            int rightWorldX = e.worldX + e.getHitBox().x + e.getHitBox().width;
            int topWorldY = e.worldY + e.getHitBox().y;
            int bottomWorldY = e.worldY + e.getHitBox().y + e.getHitBox().height;

            int leftCol = leftWorldX/gp.tileSize;
            int rightCol = rightWorldX/gp.tileSize;
            int topRow = topWorldY/gp.tileSize;
            int bottomRow = bottomWorldY/gp.tileSize;

            int tileNum1, tileNum2;
            if ((leftCol >= 0 && leftCol < gp.maxWorldCol) && (rightCol >= 0 && rightCol < gp.maxWorldCol) && (topRow >= 0 && topRow < gp.maxWorldRow) && (bottomRow >= 0 && bottomRow < gp.maxWorldRow)) {
                switch (((Player) e).direction) {
                    case "up":
                        topRow = (topWorldY - e.speed) / gp.tileSize;
                        if (topRow >= 0 && topRow < gp.maxWorldRow) {
                            tileNum1 = gp.tileMng.mapTileNum[leftCol][topRow];
                            tileNum2 = gp.tileMng.mapTileNum[rightCol][topRow];
                            if (gp.tileMng.tiles[tileNum1].collision || gp.tileMng.tiles[tileNum2].collision) {
                                e.collisionOn = true;
                            }
                        }
                        break;
                    case "left":
                        leftCol = (leftWorldX - e.speed) / gp.tileSize;
                        if (leftCol >= 0 && leftCol < gp.maxWorldCol) {
                            tileNum1 = gp.tileMng.mapTileNum[leftCol][bottomRow];
                            tileNum2 = gp.tileMng.mapTileNum[leftCol][topRow];
                            if (gp.tileMng.tiles[tileNum1].collision || gp.tileMng.tiles[tileNum2].collision) {
                                e.collisionOn = true;
                            }
                        }
                        break;
                    case "right":
                        rightCol = (rightWorldX + e.speed) / gp.tileSize;
                        if (rightCol >= 0 && rightCol < gp.maxWorldCol) {
                            tileNum1 = gp.tileMng.mapTileNum[rightCol][topRow];
                            tileNum2 = gp.tileMng.mapTileNum[rightCol][bottomRow];
                            if (gp.tileMng.tiles[tileNum1].collision || gp.tileMng.tiles[tileNum2].collision) {
                                e.collisionOn = true;
                            }
                        }
                        break;
                    case "down":
                        bottomRow = (bottomWorldY + e.speed) / gp.tileSize;
                        if (bottomRow >= 0 && bottomRow < gp.maxWorldRow) {
                            tileNum1 = gp.tileMng.mapTileNum[leftCol][bottomRow];
                            tileNum2 = gp.tileMng.mapTileNum[rightCol][bottomRow];
                            if (gp.tileMng.tiles[tileNum1].collision || gp.tileMng.tiles[tileNum2].collision) {
                                e.collisionOn = true;
                            }
                        }
                        break;
                    default:
                        System.err.println("Incorrect direction input. PLEASE REPORT");
                        break;
                }
            }
            }
        }
    }