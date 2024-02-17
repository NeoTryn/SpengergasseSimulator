package SpengergasseSimulator;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private final int originalTileSize = 16;
    private final int scale = 3;

    public final int tileSize = originalTileSize * scale;

    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    public final double screenWidth = size.getWidth(), screenHeight = size.getHeight();

    public final int maxScreenCol = (int)screenWidth / tileSize, maxScreenRow = (int)screenHeight / tileSize;

    // FPS
    public final int fps = 60;

    //World settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public KeyHandler handler = new KeyHandler();
    Thread fred;
    Player player = new Player(this, handler);
    TileManager tileMng = new TileManager(10, this);

    JFrame window;

    Inventory inv = new Inventory(new Dimension(this.tileSize * 10, this.tileSize * 16), this);
    public CollisionHandler colHandler = new CollisionHandler(this);

    public GamePanel(JFrame window) {
        this.setPreferredSize(new Dimension((int)screenWidth, (int)screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(handler);
        this.setFocusable(true);

        this.window = window;

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
        if (handler.esc) {
            window.setVisible(false);
            window.dispose();
            handler.esc = false;

            System.exit(0);
        }

        player.update();

        inv.setVisible(handler.inv);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileMng.drawFromMap(g2, 4, false);

        player.draw(g2);

        g2.dispose();
    }
}
