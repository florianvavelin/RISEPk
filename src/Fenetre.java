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
    private ArrayList<Territory> Territories = new ArrayList<>();
    private ArrayList<Player> allPlayers = new ArrayList<>();


    public Fenetre(ArrayList<Player> allPlayers, int width, int height) {
        this.allPlayers = allPlayers;
        ReadTheFileHarry();
        this.setTitle("RISK");
        this.setSize(new Dimension(width, height + 30));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


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

        JLabel contentPane = new JLabel();
        if (img != null) {
            contentPane.setIcon(new ImageIcon(img));
        }
        contentPane.setLayout(new BorderLayout());

        JPanel right = new JPanel();
        right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
        for (Player player: allPlayers
             ) {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.LINE_AXIS));
            playerPanel.setPreferredSize(new Dimension(60,40));
            JLabel Name = new JLabel(player.getName());
            Name.setText(Name.getText() + " : " + player.getColor());
            Name.setLayout(new FlowLayout(FlowLayout.LEFT));
            playerPanel.add(Name);
            right.add(playerPanel);
        }

        contentPane.add(right, BorderLayout.EAST);
        contentPane.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event, img3);
            }
        });

        initializeTerritoryWithPlayer();

        this.setContentPane(contentPane);
        this.setVisible(true);

    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
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
            System.out.println(color);
            String nigga = WhatsTerritoryNigga(color);
            //System.out.println(nigga);
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
            for (Territory territory : Territories) {
                for (int l = 0; l < 5; l++) {
                    /**
                     * The blue component is not exactly the same as set
                     * We check in a range of values of more or less 2
                     *      (for example if blue is 200, we check 198 through 202)
                     */
                    try {
                        Color color_temp = new Color(255, 255, blue - 2 + l);
                        if (color_temp.equals(territory.getColor())) {
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

    private void ReadTheFileHarry() {
        try {
            String currentLine;
            BufferedReader br = new BufferedReader(new FileReader("territoires.txt"));  // FileNotFoundException
            while ((currentLine = br.readLine()) != null) {
                String[] line = currentLine.split("/"); // Separate territory from his color
                String country = line[0]; // Name of the territory
                String color_str = line[1]; // Color of the territory
                String[] color_line = color_str.split(","); // Separate the RGB components of the color

                int r = Integer.parseInt(color_line[0]);
                int g = Integer.parseInt(color_line[1]);
                int b = Integer.parseInt(color_line[2]);
                Color color = new Color(r, g, b);

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

    /**
     * @param territory
     * This function set the ArrayList of adjacents of the territory in parameter
     */

    private void setMyMates(Territory territory) {
        try {
            String currentLine_adj;
            BufferedReader br_adj = new BufferedReader(new FileReader("adjacents.txt"));  // FileNotFoundException

            while ((currentLine_adj = br_adj.readLine()) != null) {
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

    private void initializeTerritoryWithPlayer() {
        Soldier soldier = new Soldier();
        int numberOfPlayers = allPlayers.size();
        int numberOfTerritories = Territories.size();
        for (Player player: allPlayers
                ) {
            System.out.println(player.getName() + " : " + player.getColor());
            
        }
    }
    
}