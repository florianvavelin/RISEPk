import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;


public class Fenetre extends JFrame {
    private JPanel container = new JPanel();
    private JPanel top = new JPanel();
    private JPanel middle = new JPanel();
    private JPanel bottom = new JPanel();

    private String[] choicePlayers = {"2", "3", "4", "5", "6"};
    JComboBox combo = new JComboBox(choicePlayers);
    JLabel label = new JLabel("Nombre de joueurs");
    JLabel copyright = new JLabel("Jeu de RISK créé par Florian Vavelin, Bryan To Van Trang et Marin Mouscadet");

    JButton buttonNbPlayers = new JButton("OK");
    JButton buttonPlay = new JButton("JOUER");

    private ArrayList<JTextField> namePlayers = new ArrayList<JTextField>();
    private ArrayList<JComboBox> colorPlayers = new ArrayList<JComboBox>();
    private Color[] colorOfPlayer = {Color.white, Color.black, Color.blue, Color.orange, Color.red, Color.green};

    private JLabel errorText = new JLabel();

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




        // Choose the number of players
        top.add(label);
        top.add(combo);
        top.add(buttonNbPlayers);
        container.add(top, BorderLayout.NORTH);

        // When clicking on button "OK", create the panels to create the players
        buttonNbPlayers.addActionListener(new ButtonOKListener());
        buttonPlay.addActionListener(new ButtonPlayListener());

        // Who did this
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
            namePlayers.add(field);
            players.add(field, BorderLayout.SOUTH);

            // Each player can choose a color
            String[] arraySColor = {"Blanc", "Noir", "Bleu", "Orange", "Rouge", "Vert"};
            JComboBox comboColor = new JComboBox(arraySColor);
            colorPlayers.add(comboColor);
            players.add(comboColor);

            // Add the panel of player in the parent panel
            middle.add(players);
        }

        middle.add(errorText);
        // Add the button "JOUER" to go to the game (if all information are correct)
        middle.add(buttonPlay);

        // Add the panel in the container
        container.add(middle);

        // Set it visible
        this.setContentPane(container);
        this.setVisible(true);
    }

    public void setErrorText(String type) {
        errorText.setForeground(Color.red);
        switch (type) {
            case "name":
                errorText.setText(errorText.getText() + "La case nom n'est pas remplie. ");
                break;
            case "color":
                errorText.setText(errorText.getText() + "Choisissez des couleurs différentes. ");
        }
    }

    public boolean checkDuplicates(String[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1 ; j < array.length; j++) {
                if (array[i].equals(array[j])) {
                    return true;
                }
            }
        }
        return false;
    }

    class FormListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setNumberOfPlayers(Integer.parseInt((String) combo.getSelectedItem()));
        }
    }

    class ButtonOKListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            namePlayers.clear();
            colorPlayers.clear();
            errorText.setText("");
            middle.removeAll();
            System.out.println("Ok");
            showPlayersInfo(getNumberOfPlayers());
        }
    }

    class ButtonPlayListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            errorText.setText("");
            boolean ok = true;
            String[] colors = new String[getNumberOfPlayers()];
            for (int i=0; i<colors.length; i++) {
                colors[i] = (String) colorPlayers.get(i).getSelectedItem();
            }
            if (checkDuplicates(colors)) {
                setErrorText("color");
                ok = false;
            }
            for (int i=0; i<getNumberOfPlayers(); i++) {
                String name = namePlayers.get(i).getText();
                if (name.equals("")) {
                    setErrorText("name");
                    ok = false;
                }
            }
            if (ok) {
                for (int i=0; i<getNumberOfPlayers(); i++) {
                    String name = namePlayers.get(i).getText();
                    Player player = new Player(name, colorOfPlayer[i]);
                    System.out.println(player.getName() + ": " + player.getColor());
                }
            }
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