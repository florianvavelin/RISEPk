import java.awt.*;
import javax.swing.*;
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
    private int numberOfPlayers = 2;

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Fenetre() {
        this.setTitle("Choix des joueurs");
        this.setSize(1000, 492);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        combo.addActionListener(new FormListener());
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        combo.setPreferredSize(new Dimension(100, 20));

        buttonNbPlayers.addActionListener(new BoutonListener());


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

    public void showPlayersInfo(int numberOfPlayers) {
        JPanel middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.PAGE_AXIS));
        for (int i = 1; i <= numberOfPlayers; i++) {
            JPanel players = new JPanel();
            players.setLayout(new BoxLayout(players, BoxLayout.LINE_AXIS));
            players.setPreferredSize(new Dimension(300, 30));
            players.setMaximumSize(players.getPreferredSize());

            players.add(new JLabel("Joueur " + i));

            JTextField field = new JTextField(2);
            players.add(field, BorderLayout.SOUTH);

            ArrayList<Color> arrayColor = new ArrayList();
            arrayColor.add(Color.white);
            arrayColor.add(Color.black);
            String[] arraySColor = {"blanc", "noir", "orange", "rouge"};
            JComboBox comboColor = new JComboBox(arraySColor);
            players.add(comboColor);

            middle.add(players);
        }
        container.add(middle);
        this.setContentPane(container);
        this.setVisible(true);
    }


    class FormListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Send it with a function
            setNumberOfPlayers(Integer.parseInt((String) combo.getSelectedItem()));
            System.out.println(getNumberOfPlayers());
        }
    }

    public class BoutonListener implements ActionListener {
        public void actionPerformed(ActionEvent arg0) {
            System.out.println(getNumberOfPlayers());
            showPlayersInfo(getNumberOfPlayers());
        }
    }

}