import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Pacman extends JPanel implements ActionListener {

    private boolean hra = false;
    private Image kulicka;
    private int y, x, pacman1, pacman2;
    private int pohyb_x, pohyb_y;

    public Pacman() {

        Obrazky();
        Timer();
        plocha();
    }
    private void plocha() {

        addKeyListener(new TAdapter());

        setFocusable(true);

        setBackground(Color.black);
    }

    private void Timer() {

        Timer timer = new Timer(30, this);
        timer.start();
    }

    private void hra(Graphics2D g) {
        pohyb();
        Malovani(g);
    }

    private void pohyb() {
        int Pole = 15;
        if (y % Pole == 0 && x % Pole == 0) {

            if (pohyb_x != 0 || pohyb_y != 0) {
                pacman1 = pohyb_y;
                pacman2 = pohyb_x;
            }


        }
        int rycholst = 3;
        y = y + rycholst * pacman1;
        x = x + rycholst * pacman2;
    }

    private void Malovani(Graphics2D g) {
        g.drawImage(kulicka, x + 30 , y +150, this);
    }

    private void Obrazky() {
        //kulicka = new ImageIcon("pacman.png").getImage();
        kulicka = new ImageIcon("C:\\Users\\radim\\IdeaProjects\\demo7\\Rp\\src\\pacman.png").getImage();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        grafika(g);
    }

    private void grafika(Graphics g) {
        hra((Graphics2D) g);

    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == 'q' || key == 'Q') {
                hra = true;

            } else if (hra) {
                if (key == KeyEvent.VK_LEFT) {
                    pohyb_x = -1;
                    pohyb_y = 0;
                } else if (key == KeyEvent.VK_RIGHT) {
                    pohyb_x = 1;
                    pohyb_y = 0;
                } else if (key == KeyEvent.VK_UP) {
                    pohyb_x = 0;
                    pohyb_y = -1;
                } else if (key == KeyEvent.VK_DOWN) {
                    pohyb_x = 0;
                    pohyb_y = 1;
                }

            }
        }

        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (KeyEvent.VK_LEFT == key || key == KeyEvent.VK_RIGHT
                    || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {
                pohyb_x = 0;
                pohyb_y = 0;
            }
        }
    }


}
