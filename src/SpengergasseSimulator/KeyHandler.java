package SpengergasseSimulator;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyHandler implements KeyListener {

    public boolean w, a, s, d, esc, inv;

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();


    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            w = true;
        }
        if(code == KeyEvent.VK_S) {
            s = true;
        }
        if(code == KeyEvent.VK_A) {
            a = true;
        }
        if(code == KeyEvent.VK_D) {
            d = true;
        }
//monke
        if(code == KeyEvent.VK_ESCAPE) {
            esc = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            w = false;
        }
        if(code == KeyEvent.VK_S) {
            s = false;
        }
        if(code == KeyEvent.VK_A) {
            a = false;
        }
        if(code == KeyEvent.VK_D) {
            d = false;
        }
        if(code == KeyEvent.VK_E) {
            inv = !inv;
        }
    }
}
