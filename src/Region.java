import java.util.*;

/**
 * Regions are clusters of territories.
 * This element is only useful for 2 purposes :
 *  - Checking specific missions in case of a potential winner.
 *  - Adding to the number of new recruits a player would gain at the beginning of a turn.
 */
public class Region {

    public String name;
    private ArrayList<Territory> allTerritories = new ArrayList<>();

    /**
     * Default constructor
     * NB : Not needed since several instances of it will be initialized at the beginning of a game.
     */
    public Region() {
    }


    /**
     * Default getters and setters
     */
    public void setName(String name) {
        this.name = name;
    }

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

    ////////////////////////////////////// CUSTOM METHODS /////////////////////////////////////////////////////

    /**
     * - setTheBiggest : Set the biggest territory of a map.
     * - getTheBiggest : Get the biggest territory of a map.
     */

    /**
     * Set the biggest territory of a map.
     * NB : Only useful for specific missions.
     *
     * @param listTerritoire
     */
    public void setTheBiggest(int listTerritoire) {
    }

    /**
     * Get the biggest territory of a map.
     * NB : Only useful for specific missions.
     *
     * @return region
     * TODO Set the biggest (maybe not in this class)
     */
    public Region getTheBiggest() {
        return null;
    }
}