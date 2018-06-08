import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Google is mainly a translator of txt files, commonly considered as a global database to rely on since its attributes
 * store non-changing values.
 */
public class Google {
    /**
     * List every needed element for initialization.
     */
    private ArrayList<Territory> Territories = new ArrayList<>();
    private ArrayList<Player> allPlayers = new ArrayList<>();
    private ArrayList<Region> allregions = new ArrayList<>();


    /**
     * Default constructor
     */
    public Google() {
        ReadTheFileHarry();
    }


    /** GETTER & SETTER **/

    public ArrayList<Territory> getTerritories() {
        return Territories;
    }

    public void setTerritories(ArrayList<Territory> territories) {
        Territories = territories;
    }

    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }

    public void setAllPlayers(ArrayList<Player> allPlayers) {
        this.allPlayers = allPlayers;
    }

    public ArrayList<Region> getRegions() {
        return allregions;
    }

    public void setRegions(ArrayList<Region> regions) {
        this.allregions = regions;
    }

    ////////////////////////////////////// CUSTOM METHODS /////////////////////////////////////////////////////

    /**
     * Database
     * - ReadTheFileHarry : Link every territory to its RGB color and coordinates.
     * - setMyMates : Find all adjacent territories of a territory.
     * - ONUisUseless : Dispatch territories to each region.
     *
     * Tools
     * - YouAreALizardHarry : Share territories between players at the beginning of a new game.
     * - setCoordinates : Shows images to represent each type of units on the map. - DEPRECATED
     * - getTerritoryByName : Retrieve the territory instance from its given String name.
     */

    /**
     *  Link every territory to its RGB color and coordinates.
     */
    private void ReadTheFileHarry() {
        // Retrieve RGB code from txt file to save in their respective territories
        try {
            String currentLine;
            BufferedReader br = new BufferedReader(new FileReader("territoires.txt"));  // FileNotFoundException
            while ((currentLine = br.readLine()) != null) {
                String[] line = currentLine.split("/");                          // Separate territory from his color
                String country = line[0];                                              // Name of the territory
                String color_str = line[1];                                            // Color of the territory
                String[] color_line = color_str.split(",");                      // Separate the RGB components of the color

                int r = Integer.parseInt(color_line[0]);
                int g = Integer.parseInt(color_line[1]);
                int b = Integer.parseInt(color_line[2]);
                Color color = new Color(r, g, b);

                Territory territory = new Territory(country, color);
                Territories.add(territory);
            }


            /*
            In this part, we set all the coordinates of all the territories
            Then we can put a unit in a specific territory by placing it in
            a specific set of coordinates.
             */
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("map_Yellow_1125(3).jpg"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    Color color = new Color(img.getRGB(x, y));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    if (red > 252 && green > 252 && blue < 240) {
                        for (Territory territory:Territories) {
                            if (territory.getColor().equals(new Color(255, 255, blue)) ||
                                    territory.getColor().equals(new Color(255, 255, blue+1)) ||
                                    territory.getColor().equals(new Color(255, 255, blue+2))) {
                                territory.addCoordinatesXY(x, y);
                                break;
                            }
                        }
                    }
                }
            }

            // Set all the adjacents
            for (Territory territory : Territories) {
                setMyMates(territory);
            }

            // Set the regions
            ONUisUseless();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Find all adjacent territories of a territory.
     *
     * @param territory (Territory)
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

    /**
     * Dispatch territories to each region.
     */
    private void ONUisUseless() {
        try {
            String currentLine;
            BufferedReader br = new BufferedReader(new FileReader("regions.txt"));  // FileNotFoundException
            while ((currentLine = br.readLine()) != null) {
                String[] line = currentLine.split("/");                          // Separate region from its territories
                String regionName = line[0];                                           // Name of the region
                String territories_str = line[1];                                      // Territories in the current region
                String[] territories_line = territories_str.split(",");          // Separate the territories

                Region region = new Region();
                region.setName(regionName);
                this.allregions.add(region);

                for (String territoryName : territories_line) {
                    Territory territory = getTerritoryByName(territoryName);
                    territory.setRegion(region);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Share territories between players at the beginning of a new game.
     */
    public void YouAreALizardHarry() {
        // Copy the ArrayList of territories to avoid overwring of the global ArrayList
        ArrayList<Territory> Territories = new ArrayList<>();
        Territories.addAll(this.Territories);

        ArrayList<Player> allPlayers = this.allPlayers;

        // Parsing the list of territories
        while (Territories.size() > 0) {
            // For each player
            for (Player player : allPlayers) {
                // Give a random territory to the current player
                int promised_number = (new Myfunction()).random(0, Territories.size()-1);       // Removing the last index.
                Territories.get(promised_number).setPlayer(player);

                // Remove the given territory from the copy list
                Territories.remove(promised_number);
                Territories.trimToSize();       // Updating the list size to avoid void items when removing indexes.
                if (Territories.size() == 0) {      // Error when the size can be down to 0 inside the "for" loop.
                    break;
                }
            }
        }
    }

    /**
     * DEPRECATED
     * Shows images to represent each type of units on the map.
     *
     * @param image (BufferedImage) - Image of a unit to be displayed
     * @param territory (Territory) - Territory receiving such unit
     */
    private void setCoordinates(BufferedImage image, Territory territory) {

    }

    /**
     * Retrieve the territory instance from its given String name.
     *
     * @param name (String)
     * @return territory (Territory)
     */
    private Territory getTerritoryByName(String name) {
        Territory territory = Territories.get(0);
        for (Territory territory_temp : Territories) {
            if ((territory_temp.getName()).equals(name)) {
                return territory_temp;
            }
        }
        return territory;
    }



}
