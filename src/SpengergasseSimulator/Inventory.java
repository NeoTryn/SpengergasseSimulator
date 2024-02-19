package SpengergasseSimulator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Inventory extends JPopupMenu implements Runnable{
    //da kommt ein inventory XD hätest du nicht gedacht ooder? Nigga

    public Dimension size = new Dimension();
    public int x, y;

    public GamePanel gp;

    public int maxInvCol, maxInvRow;

    Thread fred;

    public Inventory(Dimension size, GamePanel gp) {
        super();
        this.setVisible(false);
        this.size = size;
        this.setPreferredSize(size);

        this.gp = gp;

        setStandardValues();
        this.setLocation(x, y);

        startInvThread();

        this.setBorder(null);
    }

    private void setStandardValues() {
        this.x = (int)gp.screenWidth - this.size.width - 25;
        this.y = 25;
        this.maxInvCol = this.size.width / this.gp.tileSize;
        this.maxInvRow = this.size.height / this.gp.tileSize;
    }

    private void startInvThread() {
        this.fred = new Thread(this);
        fred.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        Tile tile = null;

        try {
            tile = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("img/tiles/item-Slot.png"))), false);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        int col = 0, row = 0;

        while (col < this.maxInvCol && row < this.maxInvRow) {

            int xPos = col * gp.tileSize;
            int yPos = row * gp.tileSize;

            if (this.isVisible()) {
                assert tile != null;
                g2.drawImage(tile.img,xPos, yPos, gp.tileSize, gp.tileSize, null);
            }

            col++;
            if (col == this.maxInvCol) {
                col = 0;
                row++;
            }
        }
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /gp.fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(fred != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                //update();
                repaint();
                delta--;
            }
        }
    }
}
