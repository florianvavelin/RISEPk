
import java.awt.Color;
import java.util.ArrayList;

/**
 * 
 */
public class Player {

    private String name;
    private Color color;
    private Boolean IsAnIa;
    private ArrayList<Territory> territories = new ArrayList<>();

    /**
     * Default constructor
     */
    public Player(String name, Color color, Boolean IsAnIa) {
        this.name = name;
        this.color = color;
        this.IsAnIa = IsAnIa;
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

    /**
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return
     */
    public Color getColor() {
        return this.color;
    }


    public Boolean getIsAnIa() {
        return IsAnIa;
    }

    public void setIsAnIa(Boolean anIa) {
        IsAnIa = anIa;
    }

    public ArrayList<Territory> getTerritories() {
        return territories;
    }

    public void addTerritory(Territory territory) {
        this.territories.add(territory);
    }

    public void removeTerritory(Territory territory) {
        this.territories.remove(territory);
    }
}