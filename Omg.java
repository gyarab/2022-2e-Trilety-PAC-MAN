package com.example.rp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;


public class Omg extends JPanel implements ActionListener {

    private boolean vhra = false;
    private Image kulicka, duch;
    private int y, x, pacman1, pacman2;
    private int pohyb_y, pohyb_x;
    int rychlost = 4;
    private final boolean[][] kuli = new boolean[48][33];
    int score = 0;
    private boolean vyhra = false;
    private boolean prohra = false;
    private Date zapcas;
    private int cas = 0;
    private final int[][] duchove = new int[20][2];


    public Omg() {

        Timer timer = new Timer(5000, e -> resetDuchu());
        if (!vhra) {
            timer.stop();
        }
        timer.setInitialDelay(5000);
        timer.start();
        // nastavuje reset duchů na sekundy

        generujDuchy();
        zapcas = new Date();
        Obrazky();
        Timer();
        plocha();
        int finscore = 500; //potřebné skóre
        initkul();
        JLabel scoreT = new JLabel("Potřebné tečky: " + finscore); // nastavení textu
        scoreT.setForeground(Color.white); // nastavení barvy textu na bílou
        scoreT.setFont(new Font("Verdana", Font.BOLD, 20)); // nastavení velikosti a stylu písma
        add(scoreT);

        JButton resetBtn = new JButton("Reset");
        resetBtn.addActionListener(e -> {
            // resetujeme potřebné proměnné
            resetDuchu();
            timer.start();
            zapcas = new Date();
            cas = 0;
            score = 0;
            vyhra = false;
            vhra = false;
            prohra = false;
            y = 0;
            x = 0;
            pacman1 = 0;
            pacman2 = 0;
            pohyb_x = 0;
            pohyb_y = 0;
            initkul();

        });
        add(resetBtn);
        resetBtn.setFocusable(false); // priorita tlačítka pryč


    }


    private void plocha() {

        addKeyListener(new Tlacitka());

        setFocusable(true);

        setBackground(Color.black);
    }

    private void Timer() {

        Timer timer = new Timer(30, this);
        timer.start();
    }

    private void hra(Graphics2D g) {
        if (score >= 500) {
            // pokud byla hra vyhrána, zobrazíme výherní obrazovku
            vyhobrzovka(g);
            return;
        }
        if (prohra) {
            // pokud byla hra prohrána, zobrazíme prohrávací obrazovku
            proobrazovka(g);
            return;
        }
        pohyb();
        Malovani(g);


    }

    private void vyhobrzovka(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        g.setFont(new Font("Verdana", Font.BOLD, 40));
        g.drawString("Výhra!", 450, 300);
        g.setFont(new Font("Verdana", Font.BOLD, 20));
        g.drawString("Dokázal jsi sesbírat všechny potřebné tečky", 450, 340);
        vyhra = true;
    }

    private void proobrazovka(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        g.setFont(new Font("Verdana", Font.BOLD, 40));
        g.drawString("Prohra!", 450, 300);
        g.setFont(new Font("Verdana", Font.BOLD, 20));
        g.drawString("Dokázal jsi " + score + " teček sesbírat", 450, 340);

    }

    private void pohyb() {
        //pohyb pacmana a velikost políček
        int Pole = 20;
        if (y % Pole == 0 && x % Pole == 0) {

            if (pohyb_y != 0 || pohyb_x != 0) {
                pacman1 = pohyb_y;
                pacman2 = pohyb_x;
            }
        }

        y = y + rychlost * pacman1;
        x = x + rychlost * pacman2;
    }


    private void initkul() {
        for (int i = 2; i < 48; i++) {
            for (int j = 2; j < 33; j++) {
                kuli[i][j] = true;
            }
        }
    }

    private void kulicky(Graphics2D g) {
        //generování kuliček
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 2; i < 48; i++) {
            for (int j = 2; j < 33; j++) {
                if (kuli[i][j]) {
                    int kulx = i * 20 + 9;
                    int kuly = j * 20 + 9;
                    boolean snezkul = Math.abs(x + 500 - kulx) < 10 && Math.abs(y + 265 - kuly) < 10;
                    if (!snezkul) {
                        g.fillOval(kulx, kuly, 4, 4);
                    } else {
                        kuli[i][j] = false;
                        score++;

                        if (score >= 500) {
                            rychlost = 0;
                            vhra = false;
                            JOptionPane.showMessageDialog(this, "Vyhrál jsi!! Tvůj čas: " + cas + " sekund");
                        }

                    }
                    if (snezkul) {
                        // zobrazuje aktuální poček teček
                        g.setColor(Color.white); // nastavíme barvu na bílou
                        g.setFont(new Font("Verdana", Font.BOLD, 20)); // nastavíme velikost a styl písma
                        g.drawString("Tečky: " + score, 400, 720); // zobrazíme novou hodnotu počítadla
                    }
                }
            }
        }
    }

    private void generujDuchy() {
        //generuje duchy v pozicích hlavního obdelníku
        int i = 0;
        while (i < duchove.length) {
            int x = (int) (Math.random() * 46 + 2);
            int y = (int) (Math.random() * 31 + 2);
            duchove[i][0] = x;
            duchove[i][1] = y;
            i++;

        }
    }

    private void resetDuchu() {
        generujDuchy();

    }

    private void Malovani(Graphics2D g) {
        kulicky(g); //vykreslení kuliček
        g.drawImage(kulicka, x + 500, y + 265, this); // vykreslení pac-mana
        Rectangle kulickaRect = new Rectangle(x + 500, y + 265, kulicka.getWidth(this), kulicka.getHeight(this)); //kolize pro pac-mana
        for (int[] ints : duchove) {
            int x = ints[0] * 20;
            int y = ints[1] * 20;
            g.drawImage(duch, x, y, this); // vykreslení duchů
            Rectangle DuchR = new Rectangle(x, y, duch.getWidth(this), duch.getHeight(this)); //kolize pro duchy
            if (kulickaRect.intersects(DuchR)) {
                //prohra když se duch dotkne
                prohra = true;
            }
        }
        if (vyhra) {
            return;
        }
        if (prohra) {
            return;
        }


        //steny
        g.setColor(Color.blue);
        // základní obdelník
        g.fillRect(20, 20, 20, 660);
        g.fillRect(980, 20, 20, 680);
        g.fillRect(20, 20, 960, 20);
        g.fillRect(20, 680, 960, 20);


        g.fillRect(70, 68, 855, 25); // horní podřádek
        g.fillRect(68, 129, 172, 10); // druhý podřádek shora pravý
        g.fillRect(748, 129, 185, 10); // druhý podřádek shora levý
        g.fillRect(210, 247, 570, 13); // třetí podřádek shora
        g.fillRect(70, 348, 870, 52); // čtvrtý podřádek shora
        g.fillRect(70, 449, 205, 26); // pátý podřádek shora z leva
        g.fillRect(710, 449, 210, 26); // pátý podřádek shora z prava
        g.fillRect(70, 529, 103, 11);  // šestý podřádek shora z leva
        g.fillRect(810, 529, 110, 11);  // šestý podřádek shora z prava
        g.fillRect(70, 609, 205, 25); // sedmý podřádek shora z leva
        g.fillRect(709, 609, 211, 25); // sedmý podřádek shora z prava

        g.fillRect(68, 68, 10, 65); // první sloupec zleva
        g.fillRect(923, 68, 10, 65); // první sloupec zprava
        g.fillRect(70, 540, 10, 85); // druhý sloupec shora první zleva
        g.fillRect(265, 455, 10, 170); // druhý sloupec shora druhý zleva
        g.fillRect(709, 449, 10, 175); // // druhý sloupec shora třetí zleva
        g.fillRect(910, 540, 10, 85); // druhý sloupec shora čtvrtý zleva

        g.fillRect(342, 75, 318, 180); // první obrazec
        g.fillRect(69, 169, 85, 85);  // druhý obrazec shora z leva
        g.fillRect(849, 169, 85, 85); // druhý obrazec shora z prava


        // nastavit kolize zdí
        Rectangle stenaP = new Rectangle(980, 20, 20, 680);
        if (kulickaRect.intersects(stenaP)) {
            rychlost = 4;
            pohyb_y = 0;
            pohyb_x = -1;
        }
        Rectangle stenaL = new Rectangle(20, 20, 20, 660);
        if (kulickaRect.intersects(stenaL)) {
            rychlost = 4;
            pohyb_y = 0;
            pohyb_x = 1;
        }
        Rectangle stenaH = new Rectangle(20, 20, 960, 20);
        if (kulickaRect.intersects(stenaH)) {
            rychlost = 4;
            pohyb_y = 1;
            pohyb_x = 0;
        }
        Rectangle stenaD = new Rectangle(20, 680, 960, 20);
        if (kulickaRect.intersects(stenaD)) {
            rychlost = 4;
            pohyb_y = -1;
            pohyb_x = 0;
        }

        Rectangle stenaH1 = new Rectangle(70, 68, 850, 22);
        if (kulickaRect.intersects(stenaH1)) {
            rychlost = 0;
        }
        Rectangle stenaH2L = new Rectangle(748, 129, 185, 10);
        if (kulickaRect.intersects(stenaH2L)) {
            rychlost = 0;

        }
        Rectangle stenaH2P = new Rectangle(68, 129, 172, 10);
        if (kulickaRect.intersects(stenaH2P)) {
            rychlost = 0;

        }
        Rectangle stenaH3 = new Rectangle(210, 250, 570, 10);
        if (kulickaRect.intersects(stenaH3)) {
            rychlost = 0;
        }
        Rectangle stenaH4 = new Rectangle(70, 348, 869, 52);
        if (kulickaRect.intersects(stenaH4)) {
            rychlost = 0;
        }
        Rectangle stenaH5L = new Rectangle(70, 449, 205, 26);
        if (kulickaRect.intersects(stenaH5L)) {
            rychlost = 0;
        }
        Rectangle stenaH5P = new Rectangle(710, 449, 210, 26);
        if (kulickaRect.intersects(stenaH5P)) {
            rychlost = 0;
        }
        Rectangle stenaH6L = new Rectangle(70, 529, 103, 11);
        if (kulickaRect.intersects(stenaH6L)) {
            rychlost = 0;
        }
        Rectangle stenaH6P = new Rectangle(810, 529, 110, 11);
        if (kulickaRect.intersects(stenaH6P)) {
            rychlost = 0;
        }
        Rectangle stenaH7L = new Rectangle(70, 609, 205, 25);
        if (kulickaRect.intersects(stenaH7L)) {
            rychlost = 0;
        }
        Rectangle stenaH7P = new Rectangle(709, 609, 211, 25);
        if (kulickaRect.intersects(stenaH7P)) {
            rychlost = 0;
        }
        Rectangle stenaS1L = new Rectangle(70, 75, 10, 40);
        if (kulickaRect.intersects(stenaS1L)) {
            rychlost = 0;
        }
        Rectangle stenaS1P = new Rectangle(920, 75, 10, 50);
        if (kulickaRect.intersects(stenaS1P)) {
            rychlost = 0;
        }
        Rectangle stenaS2L1 = new Rectangle(70, 540, 10, 85);
        if (kulickaRect.intersects(stenaS2L1)) {
            rychlost = 0;
        }
        Rectangle stenaS2L2 = new Rectangle(265, 455, 10, 170);
        if (kulickaRect.intersects(stenaS2L2)) {
            rychlost = 0;
        }
        Rectangle stenaS2L3 = new Rectangle(709, 449, 10, 175);
        if (kulickaRect.intersects(stenaS2L3)) {
            rychlost = 0;
        }
        Rectangle stenaS2L4 = new Rectangle(910, 540, 10, 85);
        if (kulickaRect.intersects(stenaS2L4)) {
            rychlost = 0;
        }
        Rectangle stenaO1 = new Rectangle(342, 75, 318, 180);
        if (kulickaRect.intersects(stenaO1)) {
            rychlost = 0;
        }
        Rectangle stenaO2L = new Rectangle(69, 169, 85, 85);
        if (kulickaRect.intersects(stenaO2L)) {
            rychlost = 0;
        }
        Rectangle stenaO2P = new Rectangle(849, 169, 85, 85);
        if (kulickaRect.intersects(stenaO2P)) {
            rychlost = 0;
        }
    }


    private void Obrazky() {
        //obrázky pac-mana a duchů
        kulicka = new ImageIcon("C:\\Users\\radim\\IdeaProjects\\demo7\\Rp\\src\\pacman.png").getImage();
        duch = new ImageIcon("C:\\Users\\radim\\IdeaProjects\\demo7\\Rp\\src\\duch5.jpg").getImage();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        grafika(g);
    }

    private void grafika(Graphics g) {
        hra((Graphics2D) g);

    }


    public void actionPerformed(ActionEvent e) {
        Date currentTime = new Date();
        long timeElapsed = currentTime.getTime() - zapcas.getTime();
        cas = (int) (timeElapsed / 1000);
        //počítaní času  ve hře

        repaint();
        //vykreslení obrazovky
    }

    class Tlacitka extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            // nastavení pohybu pac-mana na šipky a start hry na Z (jako začátek)
            int key = e.getKeyCode();

            if (key == 'z' || key == 'Z') {
                vhra = true;

            } else if (vhra) {
                if (key == KeyEvent.VK_UP) {
                    pohyb_y = -1;
                    pohyb_x = 0;

                } else if (key == KeyEvent.VK_DOWN) {
                    pohyb_y = 1;
                    pohyb_x = 0;

                } else if (key == KeyEvent.VK_LEFT) {
                    pohyb_y = 0;
                    pohyb_x = -1;

                } else if (key == KeyEvent.VK_RIGHT) {
                    pohyb_y = 0;
                    pohyb_x = 1;

                }

            }
        }

        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();
            //nastavení rychlosti na 4 po odentání prstu ze šipky
            if (KeyEvent.VK_LEFT == key || key == KeyEvent.VK_RIGHT
                    || key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) {

                rychlost = 4;
            }
        }
    }


}
