import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Fenetre extends JFrame {
    private ArrayList<Player> allPlayers = new ArrayList<>();
    private Google google = new Google();
    private JPanel unitsPanel = new JPanel();

    public Fenetre(ArrayList<Player> allPlayers, int width, int height) {
        this.allPlayers = allPlayers;
        this.setTitle("RISK");
        this.setSize(new Dimension(width, height + (allPlayers.size() + 1) * 20));
        this.setBackground(new Color(132,180,226));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        google.setAllPlayers(allPlayers);
        google.YouAreALizardHarry();


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

        JLabel map = new JLabel();
        if (img != null) {
            map.setIcon(new ImageIcon(img));
        }
        map.setLayout(new BorderLayout());


        JPanel contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createMatteBorder(1,1,1,1, new Color(132,180,226)));
        contentPane.setBackground(new Color(132,180,226));
        contentPane.add(map);

        /*
         Panel where we show information about all players such as their name, the number
         of territories they have.
         Also show the choices of game (players can choose the number of riders, soldiers, etc.)
          */

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
        bottomPanel.setBackground(new Color(132,180,226));
        bottomPanel.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.black));
        bottomPanel.setOpaque(true);
        for (Player player: allPlayers) {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.LINE_AXIS));
            playerPanel.setPreferredSize(new Dimension(200,15));
            playerPanel.setBackground(new Color(132,180,226));
            String type;
            if (player.getIsAnIa()) {
                type = "IA";
            } else {
                type = "Human";
            }

            JLabel Name = new JLabel(player.getName() + " (" + type + " : " +
                    player.getTerritories().size() + ")", SwingConstants.LEFT);
            Name.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            Name.setForeground(player.getColor());
            playerPanel.add(Name);
            playerPanel.setAlignmentX(0);
            bottomPanel.add(playerPanel);
        }
        bottomPanel.setAlignmentY(0);
        bottomPanel.setAlignmentX(0);
        contentPane.add(bottomPanel);

        map.setBorder(BorderFactory.createMatteBorder(1,1,1,1, new Color(255,0,0)));
        contentPane.setBorder(BorderFactory.createMatteBorder(1,1,1,1, new Color(0,255,0)));
        map.setVisible(true);

        // When clicking on the map
        map.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event, img3);
            }
        });

        setUnitsOnMap(map);

        /*JPanel bottom = new JPanel();
        contentPane.add(bottom, BorderLayout.SOUTH);

        JButton deplacement = new JButton("Déplacement");
        JButton combattre = new JButton("Combattre");
        JButton annuler = new JButton("Annuler");
        JButton findutour = new JButton("Fin du tour");

        bottom.add(combattre);
        bottom.add(deplacement);
        bottom.add(annuler);
        bottom.add(findutour); */
        this.setContentPane(contentPane);
        this.setVisible(true);
    }

    public void setUnitsOnMap(JLabel map) {
        unitsPanel.removeAll();
        /*
        Refresh the map after fight or move
         */
        this.unitsPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            ArrayList<Soldier> soldierArray = new ArrayList<>();
            soldierArray.add(new Soldier());

            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("horseman2.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Riders
            ArrayList<Rider> riderArray = new ArrayList<>();
            riderArray.add(new Rider());
            riderArray.add(new Rider());

            // Cannons
            ArrayList<Cannon> canonArray = new ArrayList<>();
            canonArray.add(new Cannon());
            canonArray.add(new Cannon());
            canonArray.add(new Cannon());

            for (Territory territory : google.getTerritories()) {
                // Put a soldier in the territory (just to try)
                territory.setArmy_soldiers(soldierArray);
                // Put 2 riders
                territory.setArmy_riders(riderArray);
                // Put 3 cannons
                territory.setArmy_cannons(canonArray);

                setUnitsPanel(g2, territory);
            }
        }
    };
        unitsPanel.setOpaque(false);
        map.add(unitsPanel);
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
                g2.fillRect(xL,yL,15,15);
            } else if (i==1 && territory.getArmy_riders().size() != 0) {
                g2.fillOval(xL,yL,15,15);
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
            int x = event.getX();
            int y = event.getY();
            Color color = new Color(img.getRGB(x, y));
            String nigga = WhatsTerritoryNigga(color);

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
                            System.out.println(territory.getName() + " : " + territory.getPlayer().getName());
                            System.out.print("Les adjacents de " + territory.getName() + " sont ");
                            for (Territory adjacents : territory.getAdjacents()) {
                                System.out.print(adjacents.getName() + ", ");
                            }
                            System.out.println("");
                            return territory.getName();
                        }
                    } catch (IllegalArgumentException iae) {
                        // Can go over 255
                        return "";
                    }
                }
            }
        }
        return "";
    }
}