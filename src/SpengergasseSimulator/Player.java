package SpengergasseSimulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity{
    private final GamePanel gp;
    private final KeyHandler keyH;

    private BufferedImage down1, down2, up1, up2, right1, right2, left1, left2;
    public String direction;

    public int screenX;
    public int screenY;

    public boolean canMoveRight = false, canMoveLeft = false, canMoveUp = false, canMoveDown = false;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        this.screenX = (int)gp.screenWidth/2 - gp.tileSize/2;
        this.screenY = (int)gp.screenHeight/2 - gp.tileSize/2;

        getPlayerImage();
        setStandardValues();
    }

    public void getPlayerImage() {
        try {

            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/player/boy_down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/player/boy_down_2.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/player/boy_up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/player/boy_up_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/player/boy_left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/player/boy_left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/player/boy_right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/player/boy_right_2.png")));

        }catch(IOException e) {
            System.out.println("ERROR");
            System.out.println(e.getMessage());
        }
    }

    private void setStandardValues() {
        this.worldX = gp.tileSize * 23;
        this.worldY = gp.tileSize * 21;
        this.speed = 4;
        this.direction = "down";

        int hitBoxX = 8, hitBoxY = 16;

        // creating the hitbox for the player
        this.hitBox = new Rectangle(hitBoxX, hitBoxY, gp.tileSize - hitBoxX * 2, gp.tileSize - hitBoxY);
    }

    public void update() {
        if (keyH.w || keyH.a || keyH.d || keyH.s) {

            if (keyH.w) {
                direction = "up";
            }
            if (keyH.s) {
                direction = "down";
            }
            if (keyH.a) {
                direction = "left";
            }
            if (keyH.d) {
                direction = "right";
            }

            collisionOn = false;
            gp.colHandler.checkTile(this);

            if (!collisionOn) {
                if (Objects.equals(direction, "up")) {
                    worldY -= speed;
                    if (canMoveUp) {
                        screenY -= speed;
                    }
                }
                if (Objects.equals(direction, "down")) {
                    worldY += speed;
                    if (canMoveDown) {
                        screenY += speed;
                    }
                }
                if (Objects.equals(direction, "left")) {
                    worldX -= speed;
                    if (canMoveLeft) {
                        screenX -= speed;
                    }
                }
                if (Objects.equals(direction, "right")) {
                    worldX += speed;
                    if (canMoveRight) {
                        screenX += speed;
                    }
                }
            }
        }

        if (!keyH.w && !keyH.a && !keyH.d && !keyH.s) {
            spriteCounter = 0;
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

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
