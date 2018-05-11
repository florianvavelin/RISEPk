import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Fenetre_1 extends JFrame {
    private JPanel container = new JPanel();
    private String[] choicePlayers = {"2", "3", "4", "5", "6"};
    JComboBox combo = new JComboBox(choicePlayers);
    JLabel label = new JLabel("Nombre de joueurs");
    JLabel copyright = new JLabel("Jeu de RISK créé par Florian Vavelin, Bryan To Van Trang et Marin Mouscadet");

    JButton buttonNbPlayers = new JButton("OK");
    JButton buttonPlay = new JButton("JOUER");

    private int numberOfPlayers = 2;
}
