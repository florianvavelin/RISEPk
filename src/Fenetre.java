import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Fenetre extends JFrame {
    private ArrayList<Player> allPlayers = new ArrayList<>();
    private Google google = new Google();
    JPanel right = new JPanel();

    public Fenetre(ArrayList<Player> allPlayers, int width, int height) {
        this.allPlayers = allPlayers;
        this.setTitle("RISK");
        this.setSize(new Dimension(width, height + allPlayers.size()*20));
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

        JLabel contentPane = new JLabel();
        if (img != null) {
            contentPane.setIcon(new ImageIcon(img));
        }
        contentPane.setLayout(new BorderLayout());

        JPanel map = new JPanel();
        map.setBorder(BorderFactory.createMatteBorder(1,1,1,1, new Color(132,180,226)));
        map.setBackground(new Color(132,180,226));
        map.add(contentPane);

        /*
         Panel where we show information about all players such as their name, the number
         of territories they have.
         Also show the choices of game (players can choose the number of riders, soldiers, etc.)
          */

        right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
        right.setBackground(new Color(132,180,226));
        right.setBorder(BorderFactory.createMatteBorder(2,0,0,0, Color.black));
        for (Player player: allPlayers) {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.LINE_AXIS));
            playerPanel.setPreferredSize(new Dimension(200,allPlayers.size()*5));
            playerPanel.setBackground(new Color(132,180,226));
            String type;
            if (player.getIsAnIa()) {
                type = "IA";
            } else {
                type = "Human";
            }
            JLabel Name = new JLabel(player.getName() + " (" + type + " : " +
                    player.getTerritories().size() + ")");
            Name.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            Name.setForeground(player.getColor());
            Name.setLayout(new FlowLayout(FlowLayout.LEFT));
            playerPanel.add(Name);
            right.add(playerPanel);
        }
        contentPane.add(right, BorderLayout.SOUTH);

        // When clicking on the map
        contentPane.addMouseListener(new MyMouseListener() {
            @Override
            public void mouseClicked(MouseEvent event) {
                super.mouseClicked(event, img3);
            }
        });


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
            /** Label pour marqué le nom du pays **/
            JLabel name = new JLabel(nigga);
            name.setLayout(new FlowLayout(FlowLayout.RIGHT));
            name.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            right.add(name);
            /** Fin du label **/
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
                for (int l = 0; l < 5; l++) {
                    /**
                     * The blue component is not exactly the same as set
                     * We check in a range of values of more or less 2
                     *      (for example if blue is 200, we check 198 through 202)
                     **/
                    try {
                        Color color_temp = new Color(255, 255, blue - 2 + l);
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