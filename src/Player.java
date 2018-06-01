
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
    private int past_territories;   // To be lastly updated when initializing a player's turn

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

    public int getPast_territories() {
        return past_territories;
    }

    public void setPast_territories(int past_territories) {
        this.past_territories = past_territories;
    }

    /**
     * Count the total of potential units earned at the beginning of a player's turn
     * @return gifts (int)
     */
    public int Christmas() {
        Google tmp_google = new Google();
        Myfunction mf = new Myfunction();

        int gifts = 0;
        int factor_one = 0;
        int factor_two = 0;
        int factor_three = 0;

        // Factor 1 : Number of owned territories
        factor_one += Math.floor(this.territories.size() / 3);

        // Factor 2 : Number of owned regions
        if (this.territories.containsAll(tmp_google.getAsia())) {
            factor_two += Math.floor(tmp_google.getAsia().size() / 2);
        }
        if (this.territories.containsAll(tmp_google.getAfrica())) {
            factor_two += Math.floor(tmp_google.getAfrica().size() / 2);
        }
        if (this.territories.containsAll(tmp_google.getOceania())) {
            factor_two += Math.floor(tmp_google.getOceania().size() / 2);
        }
        if (this.territories.containsAll(tmp_google.getEurope())) {
            factor_two += Math.floor(tmp_google.getEurope().size() / 2);
        }
        if (this.territories.containsAll(tmp_google.getNorth_america())) {
            factor_two += Math.floor(tmp_google.getNorth_america().size() / 2);
        }
        if (this.territories.containsAll(tmp_google.getSouth_america())) {
            factor_two += Math.floor(tmp_google.getSouth_america().size() / 2);
        }

        // Factor 3 : Newly conquered territories
        for (int i=0; i<this.past_territories; i++) {
            factor_three += mf.random(0,1);
        }

        // Sum and recruits
        gifts = factor_one + factor_two + factor_three;
        if (gifts < 2) {
            gifts = 2;
        }
        return gifts;
    }

    /**
     * Initializing a player's turn
     */
    public void YuGiOooooooh () {
        // Compute the number of new recruits
        this.Christmas();

        // Update the number of conquered territories (this parameter was only useful for Christmas()
        this.setPast_territories(this.territories.size());

        // Reset every unit's mouvement counter per turn to 0
        for (Territory territory : this.territories) {
            for (Soldier soldier : territory.getArmy_soldiers()) {
                soldier.setCpt(0);
            }
            for (Rider rider : territory.getArmy_riders()) {
                rider.setCpt(0);
            }
            for (Cannon cannon : territory.getArmy_cannons()) {
                cannon.setCpt(0);
            }
        }
    }
}