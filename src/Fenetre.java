import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Fenetre extends JFrame {
    private ArrayList <Territory> Territories = new ArrayList<>();
    private JPanel container = new JPanel();

    /**
     * Attributes
     * Choose the number of players
     */
    private JPanel top = new JPanel();
    private JLabel label = new JLabel("Nombre de joueurs");
    private String[] choicePlayers = {"2", "3", "4", "5", "6"};
    private JComboBox<String> combo = new JComboBox<>(choicePlayers);
    private JButton buttonNbPlayers = new JButton("OK");
    private int numberOfPlayers = 2;

    /**
     * Attributes
     * Information players
     */
    private JPanel middle = new JPanel();
    private ArrayList<JTextField> namePlayers = new ArrayList<>();
    private ArrayList<JComboBox> colorPlayers = new ArrayList<>();
    private Color[] colorOfPlayer = {Color.white, Color.black, Color.blue, Color.orange, Color.red, Color.green};
    private JLabel errorText = new JLabel();
    private JButton buttonPlay = new JButton("JOUER");

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Fenetre() {
        // General information about the panel
        this.setTitle("RISK");
        this.setSize(1125, 558+30);
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
        /**
         * Copyright
         */
        JPanel bottom = new JPanel();
        JLabel copyright = new JLabel("Jeu de RISK créé par Florian Vavelin, Bryan To Van Trang et Marin Mouscadet");
        bottom.add(copyright);
        container.add(bottom, BorderLayout.SOUTH);


        this.setContentPane(container);
        this.setVisible(true);
        //return middle;
    }

    public Fenetre(String whatIsItMan, int width, int height) {
        ReadTheFileHarry();
        if (whatIsItMan.equals("map")) {
            this.setTitle("RISK");
            this.setSize(new Dimension(width, height+30));
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setLocationRelativeTo(null);

            BufferedImage img = null;
            BufferedImage img2 = null;
            try {
                img = ImageIO.read(new File("map3.jpg"));
                img2 = ImageIO.read(new File("map_Yellow_1125.jpg"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            final BufferedImage img3 = img2;

            JLabel contentPane = new JLabel();
            if (img != null) {
                contentPane.setIcon(new ImageIcon(img));
            }
            contentPane.setLayout(new BorderLayout());
            contentPane.addMouseListener(new MyMouseListener() {
                @Override
                public void mouseClicked(MouseEvent event) {
                    super.mouseClicked(event, img3);
                }
            });

            this.setContentPane(contentPane);
            this.setVisible(true);
        }

    }

    abstract class MyMouseListener implements MouseListener {
        private void mouseClicked(MouseEvent event, BufferedImage img) {
            int x = event.getX();
            int y = event.getY();
            Color color = new Color(img.getRGB(x, y));
            System.out.println(color);
            String nigga = WhatsTerritoryNigga(color);
            System.out.println(nigga);
        }
        public void mouseEntered(MouseEvent event) {
        }
        public void mouseExited(MouseEvent event) {
        }
        public void mousePressed(MouseEvent event) {
        }
        public void mouseReleased(MouseEvent event) {
        }
    }

    private String WhatsTerritoryNigga(Color color) {
        int blue = color.getBlue();
        for (Territory territory : Territories) {
            for (int l=0; l<5; l++) {
                try {
                    Color color_temp = new Color(255, 255, blue + 2 - l);
                    if (color_temp.equals(territory.getColor())) {
                        System.out.print("Les adjacents de " + territory.getName() + " sont ");
                        for (Territory adjacents : territory.getAdjacents()) {
                            System.out.print(adjacents.getName() + ", ");
                        }
                        return territory.getName();
                    }
                } catch (IllegalArgumentException iae) {
                    return "";
                }
            }
        }
        return "";

    }

    private void ReadTheFileHarry () {
        try {
            String currentLine;
            BufferedReader br = new BufferedReader(new FileReader("territoires.txt"));  // FileNotFoundException
            while(( currentLine=br.readLine())!= null) {
                String[] line = currentLine.split("/"); // Separate territory from his color
                String country = line[0]; // Name of the territory
                String color_str = line[1]; // Color of the territory
                String[] color_line = color_str.split(","); // Separate the RGB components of the color

                int r = Integer.parseInt(color_line[0]);
                int g = Integer.parseInt(color_line[1]);
                int b = Integer.parseInt(color_line[2]);
                Color color = new Color(r,g,b);

                Territory territory = new Territory(country, color);
                Territories.add(territory);
            }

            for (Territory territory : Territories) {
                setMyMates(territory);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Territory getTerritoryByName(String name) {
        Territory territory = Territories.get(0);
        for (Territory territory_temp : Territories) {
            if ((territory_temp.getName()).equals(name)) {
                return territory_temp;
            }
        }
        return territory;
    }

    private void setMyMates(Territory territory) {
        try {
            String currentLine_adj;
            BufferedReader br_adj = new BufferedReader(new FileReader("adjacents.txt"));  // FileNotFoundException

            while (( currentLine_adj=br_adj.readLine()) != null) {
                String[] line_adj = currentLine_adj.split("/"); // Separate territory from adjacents
                if (territory.getName().equals(line_adj[0])) {
                    String[] adjacents = line_adj[1].split(",");
                    for (String adjacent : adjacents) {
                        territory.addAdjacents(getTerritoryByName(adjacent));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showPlayersInfo(int numberOfPlayers) {
        /**
         * Function to be called when choosing the number of players
         */

        // Edit the middle panel
        middle.setLayout(new BoxLayout(middle, BoxLayout.PAGE_AXIS));
        for (int i=1; i <= numberOfPlayers; i++) {
            // Create a panel for each player
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
            JComboBox<String> comboColor = new JComboBox<>(arraySColor);
            colorPlayers.add(comboColor);
            players.add(comboColor);

            // Add the panel of player in the parent panel
            middle.add(players);
        }
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel, BoxLayout.LINE_AXIS));
        errorPanel.add(errorText);
        middle.add(errorPanel);
        // Add the button "JOUER" to go to the game (if all information are correct)
        middle.add(buttonPlay);

        // Add the panel in the container
        container.add(middle, BorderLayout.CENTER);

        // Set it visible
        this.setContentPane(container);
        this.setVisible(true);
    }

    private void setErrorText(String type) {
        errorText.setForeground(Color.red);
        errorText.setPreferredSize(new Dimension(500, 30));
        errorText.setMaximumSize(errorText.getPreferredSize());
        switch (type) {
            case "name":
                errorText.setText(errorText.getText() + "Au moins une case nom n'est pas remplie. ");
                break;
            case "color":
                errorText.setText(errorText.getText() + "Choisissez des couleurs différentes. ");
                break;
        }
    }

    private boolean checkDuplicates(ArrayList<JComboBox> colorPlayers) {
        /**
         * This function checks if there are duplicated elements in an array
         * @return true if there are duplicates, false if not
         */
        for (int i = 0; i < colorPlayers.size() - 1; i++) {
            for (int j = i + 1 ; j < colorPlayers.size(); j++) {
                String temp_i = (String) colorPlayers.get(i).getSelectedItem();
                String temp_j = (String) colorPlayers.get(j).getSelectedItem();
                if (temp_i.equals(temp_j)) {
                    return true;
                }
            }
        }
        return false;
    }

    class FormListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /**
             * Everytime we change the number of players in the form, we set it in the variable
             */
            String selectedItem = (String) combo.getSelectedItem();
            if (selectedItem != null) {
                setNumberOfPlayers(Integer.parseInt(selectedItem));
            }
        }
    }

    class ButtonOKListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /*
             * Clear all information relative to the players
             * Create the panels again
             */
            namePlayers.clear();
            colorPlayers.clear();
            errorText.setText("");
            middle.removeAll();
            showPlayersInfo(getNumberOfPlayers());
        }
    }

    class ButtonPlayListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            /**
             * TODO No empty names, different colors. If ok : create the players, move to map
             * TODO Place the units (soldiers) in the map relative to the number of players
             * TODO Let the players place their units
             */
            errorText.setText("");
            boolean colorOK = true;
            boolean nameOK = true;
            for (int i=0; i<getNumberOfPlayers(); i++) {
                String name = namePlayers.get(i).getText();
                if (name.equals("")) {
                    nameOK = false;
                    setErrorText("name");
                    break;
                }
            }
            if (checkDuplicates(colorPlayers)) {
                colorOK = false;
                setErrorText("color");
            }
            if (nameOK && colorOK){
                for (int i=0; i<getNumberOfPlayers(); i++) {
                    String name = namePlayers.get(i).getText();
                    Player player = new Player(name, colorOfPlayer[i]);
                    System.out.println(player.getName() + " : " + player.getColor());
                }
            }
            // check if name is not empty, if not too long [DONE]
            // check if all players have different colors [DONE]
            // create the players [DONE]
            // go to the game
            // when going to the game, place randomly one army in each territory in the map
               // then give a mission to each player OR common mission to all
               // each player place his armies where he wants
        }
    }
}