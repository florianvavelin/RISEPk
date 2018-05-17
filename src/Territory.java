
import java.util.ArrayList;
import java.awt.Color;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;

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


    /**
     * Fights attack/defense according to the following hash map parameter : [ AttackSoldier, AttackRider, AttackCannon, DefenseSoldier, DefenseRider, DefenseCannon ]
     * Example : (2 Soldiers and 1 Cannon) in attack VS (1 Rider and 1 Cannon) in defense ==> [2, 0, 1, 0, 1, 1]
     */
    public static int [] Hajime(int [] fighters) {

        // Armies ready, attacking side and defending side
        // TreeMap naturally sorts the keys in ascending order. "Collections.reverseOrder()" reverse that order to put the most powerful unit first in the list.
        TreeMap<Double, String> attack = new TreeMap<>(Collections.reverseOrder());
        TreeMap<Double, String> defense = new TreeMap<>(Collections.reverseOrder());

        // Scoreboard on casualties
        // Same as fighters[], dead[] follows dead unit in such fashion : [ AttackSoldier, AttackRider, AttackCannon, DefenseSoldier, DefenseRider, DefenseCannon ]
        int dead[] = new int[6];

        // Priority variables added if a side got 2 units with the same power
        double first = 0.3;
        double second = 0.2;
        double third = 0.1;

        // Dice roll
        for (int role = 0; role < 6; role++) {
            while (fighters[role] > 0 ) {
                switch(role) {
                    case 0:
                        attack.put(Myfunction.random(1,6) + second, "Soldier");
                        break;
                    case 1:
                        attack.put(Myfunction.random(2,7) + first, "Rider");
                        break;
                    case 2:
                        attack.put(Myfunction.random(4,9) + third, "Cannon");
                        break;
                    case 3:
                        defense.put(Myfunction.random(1,6) + first, "Soldier");
                        break;
                    case 4:
                        defense.put( Myfunction.random(2,7) + third, "Rider");
                        break;
                    case 5:
                        defense.put(Myfunction.random(4,9) + second, "Cannon");

                        break;
                }
                fighters[role]--;
            }
        }

        // Dead people
        // -- Prepare the attacking wave
        Set attack_set = attack.entrySet();
        Iterator attack_it = attack_set.iterator();

        // -- Prepare the defending wave
        Set defense_set = defense.entrySet();
        Iterator defense_it = defense_set.iterator();

        // -- Check if such army still exists
        while ( (attack_it.hasNext()) && (defense_it.hasNext()) ) {
            // Get the name and power of the attacking unit
            Map.Entry attack_unit = (Map.Entry) attack_it.next();
            double attack_power = (Double) attack_unit.getKey();
            String attack_name = (String) attack_unit.getValue();

            // Get the name and power of the defending unit
            Map.Entry defense_unit = (Map.Entry) defense_it.next();
            double defense_power = (Double) defense_unit.getKey();
            String defense_name = (String) defense_unit.getValue();

            // Populating the graveyard
            if (Math.floor(attack_power) > Math.floor(defense_power)) {
                switch (defense_name) {
                    case "Soldier" :
                        dead[3]++;
                        break;
                    case "Rider" :
                        dead[4]++;
                        break;
                    case "Cannon" :
                        dead[5]++;
                        break;
                }
            } else {
                switch (attack_name) {
                    case "Soldier" :
                        dead[0]++;
                        break;
                    case "Rider" :
                        dead[1]++;
                        break;
                    case "Cannon" :
                        dead[2]++;
                        break;
                }
            }
        }
            // Sing the song of the fallen ones
            return(dead);

    }
}
