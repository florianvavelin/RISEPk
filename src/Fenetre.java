import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.border.TitledBorder;


public class Fenetre extends JFrame {
    private ArrayList<Player> allPlayers = new ArrayList<>();
    private Google google = new Google();
    private JPanel unitsPanel = new JPanel();
    private JPanel dashboard = new JPanel();
    private JPanel bottomPanel = new JPanel();
    private JLabel map = new JLabel();
    private Territory territoryChosenOne = null;
    private boolean waitForClick = true;

    public Fenetre(ArrayList<Player> allPlayers, int width, int height) {
        this.allPlayers = allPlayers;
        this.setTitle("RISK");
        this.setBackground(new Color(132,180,226));
        Dimension screen = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        google.setAllPlayers(allPlayers);
        google.YouAreALizardHarry();
        for (Territory territory : google.getTerritories()) {
            int[] babiesInitializer = {1,0,0};
            territory.UncleBenNeedsYou(babiesInitializer);
        }

        /**
         * MenuBar qui permettra de sauvegarder et d'autres actions
         */

        JMenuBar MenuBar = new JMenuBar();
        JMenu File = new JMenu("File");
        JMenuItem Save = new JMenuItem("Save");
        JMenuItem Exit = new JMenuItem("Exit");
        File.add(Save);
        File.add(Exit);
        MenuBar.add(File);
        this.setJMenuBar(MenuBar);

        /**
         * Create the contentPane
         */

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(132,180,226));
        this.setContentPane(contentPane);

        /**
         * Set the map in the background - map3.jpg
         * Set the map to check the color (for checking the territories - map_Yellow_1125.jpg)
         */

        BufferedImage img = null;
        BufferedImage img2 = null;
        try {
            img = ImageIO.read(new File("map3.jpg"));
            img2 = ImageIO.read(new File("map_Yellow_1125.jpg"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        final BufferedImage img3 = img2; // Must set the BufferedImage final to put it in the mouseClicked


        if (img != null) {
            map.setIcon(new ImageIcon(img));
        }
        map.setLayout(new BorderLayout());

        JPanel MapPanel = new JPanel();
        getContentPane().setLayout(null);
        MapPanel.setBounds(0, 0, width, height);
        MapPanel.setBackground(new Color(132,180,226));
        contentPane.add(MapPanel);

        MapPanel.add(map);
        contentPane.add(MapPanel);

        unitsPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                for (Territory territory : google.getTerritories()) {
                    setUnitsPanel(g2, territory);
                }
            }
        };
        unitsPanel.setOpaque(false);
        map.add(unitsPanel);

        /*
         Panel where we show information about all players such as their name, the number
         of territories they have.
         Also show the choices of game (players can choose the number of riders, soldiers, etc.)
          */


        /*
        Page axis and line axis to put the panels line by line
         */
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
        bottomPanel.setBackground(new Color(132,180,226));
        bottomPanel.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.black));
        bottomPanel.setOpaque(true);
        for (Player player: allPlayers) {
            JPanel playerPanel = new JPanel(); // Each player has a panel
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.LINE_AXIS));
            playerPanel.setPreferredSize(new Dimension(200,15));
            playerPanel.setBackground(new Color(132,180,226));
            String type;
            if (player.getIsAnIa()) {
                type = "IA";
            } else {
                type = "Human";
            }

            // JLabel is just a text, you can change the text with, in this case, Name.setText("new text");
            JLabel Name = new JLabel(player.getName() + " (" + type + " : " +
                    player.getTerritories().size() + ")", SwingConstants.LEFT);

            Name.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            Name.setForeground(player.getColor()); // change the color of the text
            playerPanel.add(Name); // add the text into the panel
            playerPanel.setAlignmentX(0);
            bottomPanel.setLocation(0,0);
            /*
            Add the panel into the bottom panel.
            Remember that each playerPanel will be placed line by line
             */
            bottomPanel.add(playerPanel);
        }
        bottomPanel.setBounds(width, 0, 300, MapPanel.getHeight()); // maybe the location

        dashboard.setLayout(new BoxLayout(dashboard, BoxLayout.LINE_AXIS));
        dashboard.setAlignmentX(0);
        dashboard.setBackground(new Color(132,180,226));

        contentPane.add(bottomPanel);
        map.setVisible(true);

        // When clicking on the map
        map.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event, img3);
            }
        });

        setUnitsOnMap();

        System.out.println(MapPanel.getHeight());
        System.out.println("width : " + bottomPanel.getWidth());

        this.setSize(new Dimension(width, MapPanel.getHeight() /*+ (bottomPanel.getHeight() + 20)*/ + 40));

        System.out.println(this.getHeight());

        this.setLocation((screen.width - this.getSize().width)/2,(screen.height - this.getSize().height)/2);
        this.setVisible(true);
    }

    public Google getGoogle() {
        return google;
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public boolean isWaitForClick() {
        return waitForClick;
    }

    public void setWaitForClick(boolean waitForClick) {
        this.waitForClick = waitForClick;
    }

    public Territory getTerritoryChosenOne() {
        return territoryChosenOne;
    }

    public void setTerritoryChosenOne(Territory territoryChosenOne) {
        this.territoryChosenOne = territoryChosenOne;
    }

    public void setDashboardPanelRelativeTo(Player player, String type, int toPlace) {

        dashboard.removeAll();
        JPanel test = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel name = new JLabel(player.getName());
        name.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        name.setBackground(player.getColor());
        if(player.getColor() == Color.black || player.getColor() == Color.blue || player.getColor() == Color.red)
        {
            name.setForeground(Color.white);
        }
        name.setOpaque(true);
        test.add(name, c);

        JLabel action = new JLabel();
        action.setText(toPlace + " soldats à placer");
        c.fill = GridBagConstraints.NONE;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        test.add(action, c);

        insertBlanck(1,test);

        JLabel rappel = new JLabel("1 cavalier = 3 soldats et 1 canon = 7 soldats");
        rappel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        c.fill = GridBagConstraints.NONE;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        test.add(rappel, c);

        insertBlanck(3,test);

        JLabel territory = new JLabel("Classique");
        territory.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 3;
        test.add(territory, c);

        /**
         * Choix du nombre de soldat
         */

        insertBlanck(5,test);

        Integer[] NbOfUnit = {1, 2, 3, 4, 5, 6};

        JLabel soldat = new JLabel("Soldats");
        soldat.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        test.add(soldat, c);
        c.gridy = 7;
        JComboBox<Integer> NbOfsoldats = new JComboBox<>(NbOfUnit);
        test.add(NbOfsoldats, c);

        JLabel cavaliers = new JLabel("Cavaliers");
        cavaliers.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 6;
        c.gridwidth = 1;
        test.add(cavaliers, c);
        c.gridy = 7;
        JComboBox<Integer> NbOfCav = new JComboBox<>(NbOfUnit);
        test.add(NbOfCav, c);

        JLabel canons = new JLabel("Canons");
        canons.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 6;
        c.gridwidth = 1;
        test.add(canons, c);
        c.gridy = 7;
        JComboBox<Integer> NbOfCan = new JComboBox<>(NbOfUnit);
        test.add(NbOfCan, c);


        insertBlanck(8,test);

        JButton cancel = new JButton("Annuler");
        cancel.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        test.add(cancel, c);

        JButton validate = new JButton("Valider");
        validate.setFont(new Font("TimesRoman", Font.PLAIN, 15));
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 9;
        c.gridwidth = 1;
        test.add(validate, c);


        dashboard.add(test);
        bottomPanel.add(dashboard);

        validate();
        repaint();

    }

    public void insertBlanck(int row, JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        JLabel blank = new JLabel(" ");
        blank.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        panel.add(blank, c);
    }

    public void setDashboardPanel() {
        /*
        topDashboard.setSize(new Dimension(bottomPanel.getWidth(), 50));
        JPanel namePlayerPanel = new JPanel();
        namePlayerPanel.setPreferredSize(new Dimension(topDashboard.getWidth()/3, topDashboard.getHeight()));
        namePlayerPanel.setBackground(colorPlayer);
        JLabel namePlayerText = new JLabel();
        namePlayerPanel.setPreferredSize(new Dimension(namePlayerPanel.getWidth(),10));

        JPanel nameTitlePanel = new JPanel();
        nameTitlePanel.setPreferredSize(new Dimension(2*topDashboard.getWidth()/3, topDashboard.getHeight()));
        JLabel titleText = new JLabel();
        if (colorPlayer.equals(Color.green) || colorPlayer.equals(Color.white)) {
            namePlayerText.setForeground(Color.black);
            titleText.setForeground(Color.black);
        } else {
            namePlayerText.setForeground(Color.white);
            titleText.setForeground(Color.white);
        }
        titleText.setForeground(Color.black);
        titleText.setText("Renforts");
        nameTitlePanel.add(titleText);

        namePlayerText.setText(player.getName());
        namePlayerPanel.add(namePlayerText);

        topDashboard.add(namePlayerPanel);
        topDashboard.add(nameTitlePanel);

        JPanel middleDashboard = new JPanel();

        JPanel bottomDashboard = new JPanel();

        dashboard.add(topDashboard);
        dashboard.add(middleDashboard);
        dashboard.add(bottomDashboard);
        bottomPanel.add(dashboard);*/

    }

    public void setUnitsOnMap() {
        repaint();
    }

    private void setUnitsPanel(Graphics2D g2, Territory territory) {
        Player playerInTerritory = territory.getPlayer();
        Color colorToDraw = playerInTerritory.getColor();
        ArrayList<ArrayList<Integer>> coordinatesXY = territory.getCoordinatesXY();

        int[][] unitsCoordinates = {territory.getSoldierCoordinates(), territory.getRidersCoordinates(),
                                    territory.getCannonsCoordinates()};

        for (int i = 0; i < unitsCoordinates.length; i++) {
            int temp1 = i==2? 0 : 2;
            int temp2 = i==1? 0 : 1;
            if (unitsCoordinates[i][0] == 0 &&
                    unitsCoordinates[i][1] == 0) {
                boolean ok;
                do {
                    int rand = (new Myfunction()).random(0, coordinatesXY.size() - 1);
                    int x = coordinatesXY.get(rand).get(0);
                    int y = coordinatesXY.get(rand).get(1);
                    int xCoord1 = unitsCoordinates[temp1][0];
                    int yCoord1 = unitsCoordinates[temp1][1];
                    int xCoord2 = unitsCoordinates[temp2][0];
                    int yCoord2 = unitsCoordinates[temp2][1];
                    if (i==0) {
                        territory.setSoldierCoordinates(x,y);
                    } else if (i==1) {
                        territory.setRidersCoordinates(x,y);
                    } else {
                        territory.setCannonsCoordinates(x,y);
                    }
                    ok = Math.sqrt((y - yCoord1) * (y - yCoord1) + (x - xCoord1) * (x - xCoord1)) < 21
                      && Math.sqrt((y - yCoord2) * (y - yCoord2) + (x - xCoord2) * (x - xCoord2)) < 21
                      && Math.sqrt((yCoord1 - yCoord2) * (yCoord1 - yCoord2) + (xCoord1 - xCoord2) * (xCoord1 - xCoord2)) < 21;
                } while (ok);
            }
            int xL = unitsCoordinates[i][0];
            int yL = unitsCoordinates[i][1];
            g2.setColor(colorToDraw);
            if (i==0 && territory.getArmy_soldiers().size() != 0) {
                g2.fillOval(xL,yL,15,15);
            } else if (i==1 && territory.getArmy_riders().size() != 0) {
                g2.fillRect(xL,yL,15,15);
            } else if (i==2 && territory.getArmy_cannons().size() != 0){
                g2.fillRoundRect(xL,yL,15,15, 2,2);
            }
            Color colorText = Color.white;
            if (colorToDraw.equals(Color.white) || colorText.equals(Color.green)) {
                colorText = Color.black;
            }
            g2.setColor(colorText);
            if (i==0 && territory.getArmy_soldiers().size() != 0) {
                g2.drawString(String.valueOf(territory.getArmy_soldiers().size()), xL+4,yL+11);
            } else if (i==1 && territory.getArmy_riders().size() != 0) {
                g2.drawString(String.valueOf(territory.getArmy_riders().size()), xL+4,yL+11);
            } else if (i==2 && territory.getArmy_cannons().size() != 0) {
                g2.drawString(String.valueOf(territory.getArmy_cannons().size()), xL+4,yL+11);
            }
        }
    }

    abstract class MyMouseListener implements MouseListener {
        /**
         * @param event
         * @param img to check the color
         *            We set it one time, otherwise we'll have to open it every time we click on the map
         */
        private void mouseClicked(MouseEvent event, BufferedImage img) {
            if (waitForClick) {
                int x = event.getX();
                int y = event.getY();
                Color color = new Color(img.getRGB(x, y));
                if(event.getButton() == MouseEvent.BUTTON1) {
                    //System.out.print("Left Click : ");
                }
                if(event.getButton() == MouseEvent.BUTTON2) {
                    //System.out.print("Middle Click : ");
                }
                if(event.getButton() == MouseEvent.BUTTON3) {
                    //System.out.print("Right Click : ");
                }
                territoryChosenOne = WhatsTerritoryNigga(color);
                if (territoryChosenOne != null) {
                    //System.out.println(territoryChosenOne.getName());
                }
            } else {
                territoryChosenOne = null;
            }

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

    /**
     * Checks which territory matches the color in parameter
     * @param color : each territory has a specific color in the map_Yellow image
     * @return name of the territory
     */
    private Territory WhatsTerritoryNigga(Color color) {
        int red = color.getRed();
        int green = color.getGreen();
        if (red > 253 && green > 253) {
            int blue = color.getBlue();
            for (Territory territory : google.getTerritories()) {
                for (int l = 0; l < 3; l++) {
                    /**
                     * The blue component is not exactly the same as set
                     * We check in a range of values of more or less 2
                     *      (for example if blue is 200, we check 198 through 202)
                     **/
                    try {
                        Color color_temp = new Color(255, 255, blue - 1 + l);
                        if (color_temp.equals(territory.getColor())) {
                            //System.out.println(territory.getName() + " : " + territory.getPlayer().getName());
                            //System.out.print("Les adjacents de " + territory.getName() + " sont ");
                            for (Territory adjacents : territory.getAdjacents()) {
                                //System.out.print(adjacents.getName() + ", ");
                            }
                            //System.out.println("");
                            return territory;
                        }
                    } catch (IllegalArgumentException iae) {
                        // Can go over 255
                        return null;
                    }
                }
            }
        }
        return null;
    }

}