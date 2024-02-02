package SpengergasseSimulator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private final int originalTileSize = 16;
    private final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    private final int maxScreenCol = 16, maxScreenRow = 12;

    private final int screenWidth = tileSize * maxScreenCol, screenHeight = tileSize * maxScreenRow;

    private final int fps = 60;

    KeyHandler handler = new KeyHandler();
    Thread fred;
    Player player = new Player(this, handler);
    TileManager tileMng = new TileManager(10, this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(handler);
        this.setFocusable(true);

        tileMng.loadTiles(0, 9);
    }

    public void startGameThread() {
        fred = new Thread(this);
        fred.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 /fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(fred != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;


        int x = 0, y = 0;

        for (int i = 0; i < 12; i++) {
            for (int j = 0;j < 16; j++) {
                tileMng.draw(g2, 0, x, y);
                x += 48;
            }
            x = 0;
            y += 48;
        }

        player.draw(g2);

        g2.dispose();
    }
}
