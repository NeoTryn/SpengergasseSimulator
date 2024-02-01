package SpengergasseSimulator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

            down1 = ImageIO.read(getClass().getResourceAsStream("C:\\Users\\felix\\IdeaProjects\\SpengergasseSimulator2\\src\\img\\boy_down_1.png"));
            /*down2 = ImageIO.read(getClass().getResourceAsStream("./src/img/boy_down_2.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("./src/img/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("./src/img/boy_up_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("./src/img/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("./src/img/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("./src/img/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("./src/img/boy_right_2.png"));*/

        }catch(IOException e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    private void setStandardValues() {
        this.x = 100;
        this.y = 100;
        this.speed = 4;
        this.direction = "down";
    }

    public void update() {
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
    }

    public void draw(Graphics2D g2) {
        //g2.setColor(Color.white);
        //g2.fillRect(this.x, this.y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                image = up1;
                break;
            case "left":
                image = left1;
                break;
            case "right":
                image = right1;
                break;
            case "down":
                image = down1;
                break;
            default:
                System.out.println("Invalid direction");
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
