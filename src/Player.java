
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 */
public class Player {

    private String name;
    private Color color;
    private Boolean IsAnIa;
    private Mission mission;
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

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    /**
     * Count the total of potential units earned at the beginning of a player's turn
     * @return gifts (int)
     */
    public int Christmas() {
        ArrayList<Region> arrayRegion = new ArrayList<>();
        Set<Region> setRegion = new HashSet<>();

        int gifts;
        int factor_one = 0;
        int factor_two = 0;
        int factor_three = 0;

        // Factor 1 : Number of owned territories
        factor_one += Math.floor(this.territories.size() / 3);

        // Factor 2 : Number of owned regions
        for (Territory territory : this.territories) {
            arrayRegion.add(territory.getRegion());
        }
        setRegion.addAll(arrayRegion);
        arrayRegion.clear();
        arrayRegion.addAll(setRegion);

        for (Region region : arrayRegion) {
            if (this.territories.containsAll(region.getAllTerritories())) {
                factor_two += Math.floor(region.getAllTerritories().size() / 2);
            }
        }

        // Factor 3 : Newly conquered territories
        for (int i=0; i<this.past_territories; i++) {
            factor_three += (new Myfunction()).random(0,1);
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

    /**
     * Store remaining units
     * @param nigga (int)
     * @param gang = [Soldier, Rider, Cannon] (int [])
     * @return nigga (int)
     */
    public int HelpmeNIGGA (int nigga, int [] gang, Territory bled) {
        // Check if the player has enough remaning units
        if (nigga < (gang[0] + gang[1] + gang[2])) {
            return nigga; // Return an unchanged remaining number of units
        }

        // Set units in the chosen territory
        else {
            bled.UncleBenNeedsYou(gang);
        }

        // Remove set units from the remaining units
        nigga -= gang[0] + gang[1] + gang[2];
        return nigga;
    }

    /**
     * Compute max number of units for each type
     * @param lemons (int)
     * @return lemonade = [Soldier, Rider, Cannon] (int [])
     */
    public int [] MixTheJuice (int lemons) {
        Soldier soldier = new Soldier();
        int max_soldier = (int) Math.floor(lemons / soldier.getCost());

        Rider rider = new Rider();
        int max_rider = (int) Math.floor(lemons / rider.getCost());

        Cannon cannon = new Cannon();
        int max_cannon = (int) Math.floor(lemons / cannon.getCost());

        int[] lemonade = {max_soldier, max_rider, max_cannon};
        return lemonade;
    }

    /**
     * Compute the cost of the current set of units
     * @param lemonade = [Soldier, Rider, Cannon] (int [])
     * @return lemons (int)
     */
    public int DrinkTheJuice (int [] lemonade) {
        Soldier soldier = new Soldier();
        Rider rider = new Rider();
        Cannon cannon = new Cannon();

        int lemons = lemonade[0]*soldier.getCost() + lemonade[1]*rider.getCost() + lemonade[2]*cannon.getCost();
        return lemons;
    }
}