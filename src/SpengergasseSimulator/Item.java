package SpengergasseSimulator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Item extends JComponent{
     public boolean canEquip;
     public boolean pickedUp = false;
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

     public void pickUp() {

     }


}
