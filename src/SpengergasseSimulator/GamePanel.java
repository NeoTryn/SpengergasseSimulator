package SpengergasseSimulator;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private final int originalTileSize = 16;
    private final int scale = 3;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16, maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol, screenHeight = tileSize * maxScreenRow;

    // FPS
    public final int fps = 60;

    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    KeyHandler handler = new KeyHandler();
    Thread fred;
    Player player = new Player(this, handler);
    TileManager tileMng = new TileManager(10, this);
    public CollisionHandler colHandler = new CollisionHandler(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(handler);
        this.setFocusable(true);

        tileMng.loadTiles(0, 9);
        tileMng.loadMap("maps/world01.txt");
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

        tileMng.drawFromMap(g2);

        player.draw(g2);

        g2.dispose();
    }
}
