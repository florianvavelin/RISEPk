import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class Google {
    private ArrayList<Territory> Territories = new ArrayList<>();
    private ArrayList<Player> allPlayers = new ArrayList<>();

    public Google() {
        ReadTheFileHarry();
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


            /*
            In this part, we set all the coordinates of all the territories
            Then we can put a unit in a specific territory by placing it in
            a specific set of coordinates.
             */
            BufferedImage img = null;
            try {
                img = ImageIO.read(new File("map_Yellow_1125(2).jpg"));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            for (int x = 0; x < img.getWidth(); x++) {
                for (int y = 0; y < img.getHeight(); y++) {
                    Color color = new Color(img.getRGB(x, y));
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    if (red > 253 && green > 253 && blue < 240) {
                        for (Territory territory:Territories) {
                            if (territory.getColor().equals(new Color(255, 255, blue + 1)) ||
                                    territory.getColor().equals(new Color(255, 255, blue))) {
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



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

    private void setCoordinates(BufferedImage image, Territory territory) {

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

    /**
     * Share territories between players at the beginning of a new game.
     */
    public void YouAreALizardHarry() {
        ArrayList<Territory> Territories = new ArrayList<>();
        // Copy the ArrayList of territories otherwise it will affect the actual ArrayList and not a copy
        Territories.addAll(this.Territories);
        ArrayList<Player> allPlayers = this.allPlayers;
        while (Territories.size() > 0) {
            for (Player player : allPlayers) {
                int promised_number = (new Myfunction()).random(0, Territories.size()-1); // remove the last index
                Territories.get(promised_number).setPlayer(player);
                Territories.remove(promised_number);
                Territories.trimToSize();
                if (Territories.size() == 0) {
                    // size can be zero inside the for loop
                    break;
                }
            }
        }
    }
}
