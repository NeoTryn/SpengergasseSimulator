package SpengergasseSimulator;

import javax.swing.*;
import java.awt.*;

public class Inventory extends JPopupMenu{
    //da kommt ein inventory XD hätest du nicht gedacht ooder? Nigga

    public Dimension size = new Dimension();
    public int x, y;

    public GamePanel gp;

    public Inventory(Dimension size, GamePanel gp) {
        super();
        this.setVisible(false);
        this.size = size;
        this.setPreferredSize(size);

        this.gp = gp;

        setStandardValues();
        this.setLocation(x, y);
    }

    private void setStandardValues() {
        this.x = (int)gp.screenWidth - 400;
        this.y = 25;
    }
}
