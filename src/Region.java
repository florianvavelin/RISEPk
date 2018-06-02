
import java.util.*;


public class Region {

    public String name;
    private ArrayList<Territory> allTerritories = new ArrayList<>();

    public Region() {
    }


    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    public ArrayList<Territory> getAllTerritories() {
        return allTerritories;
    }

    public void setAllTerritories(ArrayList<Territory> allTerritories) {
        this.allTerritories = allTerritories;
    }

    public void addInAllTerritories(Territory territory) {
        this.allTerritories.add(territory);
    }

    /**
     * @param listTerritoire
     */
    public void setTheBiggest(int listTerritoire) {
    }

    /**
     * @return
     * TODO Set the biggest (maybe not in this class)
     */
    public Region getTheBiggest() {
        return null;
    }
}