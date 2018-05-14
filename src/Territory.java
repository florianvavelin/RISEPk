
import java.util.ArrayList;
import java.awt.Color;


/**
 * 
 */
public class Territory {

    private String name;
    private ArrayList<Territory> adjacents;

    /**
     * Each territory will have a specific color.
     * This color will be used to differentiate all the territories
     * If the region is red, for example, only one component of the RGB color
     * will be slightly different
     */
    private Color color;

    /**
     * Default constructor
     */
    public Territory(String name, ArrayList<Territory> adjacents, Color color) {
        this.name = name;
        this.adjacents = adjacents;
        this.color = color;
    }





    /**
     * @param name
     */
    public void setName(String name) {
        // TODO implement here
        this.name = name;
    }

    /**
     * @return
     */
    public String getName() {
        // TODO implement here
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

    /**
     * @param territory
     */
    public void setAdjacents(Territory territory) {
        // TODO implement here
    }

    /**
     * @return
     */
    public ArrayList<Territory> getAdjacents() {
        // TODO implement here
        return null;
    }

}