package com.example.rp;

import java.awt.*;
import javax.swing.*;

public class Start extends JFrame {

    public Start() {
// zacatecni obarazovka a nastavení herního okna
        Spusteni();
        JOptionPane.showMessageDialog(this, "Hra se spuští tlačítkem \"Z\" (jako Začátek)");
        this.setTitle("PAC-MAN");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1050, 800);

    }

    private void Spusteni() {

        add(new Omg());
    }


    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ch = new Start();
            ch.setVisible(true);

        });
    }

}

