package SpengergasseSimulator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Item extends JComponent{
     public boolean canEquip;
     public boolean collisionOn = false;
     public BufferedImage img;
     public int worldX = 800, worldY = 800;
     public GamePanel gp;

     public Item(boolean canEquip, GamePanel gp) {
         super();

         this.canEquip = canEquip;

         this.gp = gp;

         getTileImage();
     }

     public void getTileImage() {
         try {
             img = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/water13.png")));
         }
         catch (IOException e) {
             System.err.println(e.getMessage());
         }
     }

     public void draw(Graphics2D g2) {

         int screenX = worldX - gp.player.worldX + gp.player.screenX;
         int screenY = worldY - gp.player.worldY + gp.player.screenY;

         if (worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                 worldX > gp.player.worldX - gp.player.screenX - gp.tileSize &&
                 worldY < gp.player.worldY + gp.player.screenY + gp.tileSize &&
                 worldY > gp.player.worldY - gp.player.screenY - gp.tileSize) {
             g2.drawImage(img, screenX, screenY, gp.tileSize, gp.tileSize, null);
         }
     }

    public void checkCollision(Player e) {
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
            switch (e.direction) {
                case "up":
                    topRow = (topWorldY - e.speed) / gp.tileSize;
                    if (topRow >= 0 && topRow < gp.maxWorldRow) {
                        tileNum1 = gp.tileMng.mapTileNum[leftCol][topRow];
                        tileNum2 = gp.tileMng.mapTileNum[rightCol][topRow];
                        if (gp.tileMng.tiles[tileNum1].collision || gp.tileMng.tiles[tileNum2].collision) {
                            this.collisionOn = true;
                        }
                    }
                    break;
                case "left":
                    leftCol = (leftWorldX - e.speed) / gp.tileSize;
                    if (leftCol >= 0 && leftCol < gp.maxWorldCol) {
                        tileNum1 = gp.tileMng.mapTileNum[leftCol][bottomRow];
                        tileNum2 = gp.tileMng.mapTileNum[leftCol][topRow];
                        if (gp.tileMng.tiles[tileNum1].collision || gp.tileMng.tiles[tileNum2].collision) {
                            this.collisionOn = true;
                        }
                    }
                    break;
                case "right":
                    rightCol = (rightWorldX + e.speed) / gp.tileSize;
                    if (rightCol >= 0 && rightCol < gp.maxWorldCol) {
                        tileNum1 = gp.tileMng.mapTileNum[rightCol][topRow];
                        tileNum2 = gp.tileMng.mapTileNum[rightCol][bottomRow];
                        if (gp.tileMng.tiles[tileNum1].collision || gp.tileMng.tiles[tileNum2].collision) {
                            this.collisionOn = true;
                        }
                    }
                    break;
                case "down":
                    bottomRow = (bottomWorldY + e.speed) / gp.tileSize;
                    if (bottomRow >= 0 && bottomRow < gp.maxWorldRow) {
                        tileNum1 = gp.tileMng.mapTileNum[leftCol][bottomRow];
                        tileNum2 = gp.tileMng.mapTileNum[rightCol][bottomRow];
                        if (gp.tileMng.tiles[tileNum1].collision || gp.tileMng.tiles[tileNum2].collision) {
                            this.collisionOn = true;
                        }
                    }
                    break;
                default:
                    System.err.println("Incorrect direction input. PLEASE REPORT");
                    break;
            }
        }
    }

    public void drawInInv(int invSlot, Graphics2D g2) {
         int screenX = gp.inv.x, screenY = gp.inv.y;

         if (invSlot > gp.inv.maxInvCol) {
             for (int i = 0; i < Math.ceil((double) invSlot / gp.inv.maxInvCol); i++) {
                 screenY += 48;
             }

             for (int i = 0; i < invSlot % gp.inv.maxInvCol; i++) {
                 screenX += 48;
             }
         }
         else {
             for (int i = 0; i < invSlot; i++) {
                 screenX += 48;
             }
         }

         if (gp.inv.isVisible()) {
             g2.drawImage(this.img, screenX, screenY, gp.tileSize, gp.tileSize, null);
         }
    }
}
