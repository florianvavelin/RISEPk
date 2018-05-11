import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Fenetre extends JFrame {
    private JPanel container = new JPanel();
    private String[] choicePlayers = {"2", "3", "4", "5", "6"};
    JComboBox combo = new JComboBox(choicePlayers);
    JLabel label = new JLabel("Nombre de joueurs");
    JLabel copyright = new JLabel("Jeu de RISK créé par Florian Vavelin, Bryan To Van Trang et Marin Mouscadet");

    JButton buttonNbPlayers = new JButton("OK");
    JButton buttonPlay = new JButton("JOUER");

    private int numberOfPlayers = 2;

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Fenetre() {
        // General information about the panel
        this.setTitle("Choix des joueurs");
        this.setSize(1000, 492);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        combo.addActionListener(new FormListener());
        container.setBackground(Color.white);
        container.setLayout(new BorderLayout());
        combo.setPreferredSize(new Dimension(100, 20));

        // When clicking on button "OK", create the panels to create the players
        // Goes wrong if clicking again
        buttonNbPlayers.addActionListener(new ButtonOKListener());


        // Choose the number of players
        JPanel top = new JPanel();
        top.add(label);
        top.add(combo);
        top.add(buttonNbPlayers);
        container.add(top, BorderLayout.NORTH);


        // Who did this
        JPanel bottom = new JPanel();
        bottom.add(copyright);
        container.add(bottom, BorderLayout.SOUTH);


        this.setContentPane(container);
        this.setVisible(true);
        //return middle;
    }

    public void showPlayersInfo(int numberOfPlayers) {

        /**
         * Function to be called when choosing the number of players
         */
        //Creation of the middle panel
        JPanel middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.PAGE_AXIS));

        for (int i = 1; i <= numberOfPlayers; i++) {

            JPanel players = new JPanel();
            players.setLayout(new BoxLayout(players, BoxLayout.LINE_AXIS));
            players.setPreferredSize(new Dimension(300, 30));
            players.setMaximumSize(players.getPreferredSize());

            // Joueur 1, Joueur 2, etc.
            players.add(new JLabel("Joueur " + i + " "));

            // Field for the pseudo of each player
            JTextField field = new JTextField(2);
            players.add(field, BorderLayout.SOUTH);

            // Each player can choose a color
            String[] arraySColor = {"Blanc", "Noir", "Bleu", "Orange", "Rouge", "Vert"};
            JComboBox comboColor = new JComboBox(arraySColor);
            players.add(comboColor);

            // Add the panel of player in the parent panel
            middle.add(players);
        }
        // Add the button "JOUER" to go to the game (if all information are correct)
        middle.add(buttonPlay);


        // Add the panel in the container
        container.add(middle);

        // Set it visible
        this.setContentPane(container);
        this.setVisible(true);
    }

    class FormListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setNumberOfPlayers(Integer.parseInt((String) combo.getSelectedItem()));
        }
    }

    class ButtonOKListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            showPlayersInfo(getNumberOfPlayers());
        }
    }

    class ButtonPlayListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // check if name is not empty, if not too long
            // check if all players have different colors
            // create the players
            // go to the game
            // when going to the game, place randomly one army in each territory in the map
               // then give a mission to each player OR common mission to all
               // each player place his armies where he wants
        }
    }

}