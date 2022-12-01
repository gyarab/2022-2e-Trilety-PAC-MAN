import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main extends JFrame {

    public Main() {
        Spusteni();
    }

    private void Spusteni() {

        add(new Pacman());
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ch = new Main();
            ch.setVisible(true);
        });
    }
}
