
import java.awt.Color;

/**
 * 
 */
public class Player {

    private String name;
    private Color color;
    private Boolean IsAnIa;

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


    public Boolean getAnIa() {
        return IsAnIa;
    }

    public void setAnIa(Boolean anIa) {
        IsAnIa = anIa;
    }
}