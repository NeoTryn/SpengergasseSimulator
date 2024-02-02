package SpengergasseSimulator;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage img;
    public boolean collision = false;

    public Tile(BufferedImage img, boolean collision) {
        this.img = img;
        this.collision = collision;
    }
}
