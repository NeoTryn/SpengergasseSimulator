package SpengergasseSimulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    private GamePanel gp;
    private KeyHandler keyH;

    private BufferedImage down1, down2, up1, up2, right1, right2, left1, left2;
    private String direction;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        getPlayerImage();
        setStandardValues();
    }

    public void getPlayerImage() {
        try {

            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/boy_down_2.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/boy_up_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/boy_right_2.png")));

        }catch(IOException e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
    }

    private void setStandardValues() {
        this.x = 100;
        this.y = 100;
        this.speed = 4;
        this.direction = "down";
    }

    public void update() {

        if (!keyH.w && !keyH.a && !keyH.d && !keyH.s) {
            spriteCounter = 0;
        }

        if (keyH.w) {
            direction = "up";
            y -= speed;
        }
        if(keyH.s) {
            direction = "down";
            y += speed;
        }
        if(keyH.a) {
            direction = "left";
            x -= speed;
        }
        if(keyH.d) {
            direction = "right";
            x += speed;
        }

        spriteCounter++;
        if (spriteCounter > 14) {
            if (spriteNum == 1) {
                spriteNum = 2;
            }
            else if(spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        //g2.setColor(Color.white);
        //g2.fillRect(this.x, this.y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                else if (spriteNum == 2) {
                    image = up2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                else if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                else if (spriteNum == 2) {
                    image = right2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            default:
                System.out.println("Invalid direction");
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
