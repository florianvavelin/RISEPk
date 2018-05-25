import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
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
}
