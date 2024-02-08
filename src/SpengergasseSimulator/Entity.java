package SpengergasseSimulator;

import java.awt.*;

public class Entity {
    protected int worldX, worldY, speed;
    protected int spriteNum = 1, spriteCounter = 0;
    protected Rectangle hitBox;
    public boolean collisionOn;

    public int getX() {
        return worldX;
    }

    public void setX(int worldX) {
        this.worldX = worldX;
    }

    public int getY() {
        return worldY;
    }

    public void setY(int worldY) {
        this.worldY = worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }
}
