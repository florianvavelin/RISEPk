import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Fenetre extends JFrame {
    private JPanel container = new JPanel();
    private String[] choicePlayers = {"2", "3", "4", "5", "6"};
    private JComboBox combo = new JComboBox(choicePlayers);
    private JLabel label = new JLabel("Nombre de joueurs");
    private JLabel copyright = new JLabel("Jeu de RISK créé par Florian Vavelin, Bryan To Van Trang et Marin Mouscadet");

    private JButton buttonNbPlayers = new JButton("OK");
    private JButton buttonPlay = new JButton("JOUER");

    private ArrayList<Player> players = new ArrayList();


    public Fenetre(){
        this.setTitle("Choix des joueurs");
        this.setSize(1000, 492);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        combo.addActionListener(new FormListener());
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        combo.setPreferredSize(new Dimension(100, 20));


        JPanel top = new JPanel();
        top.add(label);
        top.add(combo);
        top.add(buttonNbPlayers);
        container.add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.add(copyright);
        container.add(bottom, BorderLayout.SOUTH);


        this.setContentPane(container);
        this.setVisible(true);

    }

    class FormListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            // Send it with a function
            System.out.println("ActionListener : action sur " + combo.getSelectedItem());
        }
    }

}