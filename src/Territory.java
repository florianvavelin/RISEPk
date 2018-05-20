import java.util.ArrayList;
import java.awt.Color;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;


public class Territory {



    //
    // Attributes //////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    private String name;
    private ArrayList<Territory> adjacents = new ArrayList<>();

    /**
     * Each territory will have a specific color.
     * This color will be used to differentiate all the territories.
     */
    private Color color;

    /**
     * Each territory can have a player, and therefore armies within affiliated only to this player.
     */
    private Player player;
    private ArrayList<Soldier> army_soldiers = new ArrayList<>();
    private ArrayList<Rider> army_riders = new ArrayList<>();
    private ArrayList<Cannon> army_cannons = new ArrayList<>();



    //
    // Default constructor /////////////////////////////////////////////////////////////////////////////////////////////
    //
    public Territory(String name, Color color) {
        this.name = name;
        this.color = color;
    }



    //
    // Getters and setters /////////////////////////////////////////////////////////////////////////////////////////////
    //
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdjacents(ArrayList<Territory> adjacents) {
        this.adjacents = adjacents;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Territory> getAdjacents() {
        return adjacents;
    }



    //
    // Methods /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    /**
     * @param territory (Territory)
     * Set the adjacents list adding the territory in it.
     */
    public void addAdjacents(Territory territory) {
        adjacents.add(territory);
    }


    /**
     * Fights attack/defense according to the following hash map parameter :
     * [ AttackSoldier, AttackRider, AttackCannon, DefenseSoldier, DefenseRider, DefenseCannon ]
     * Example : (2 Soldiers and 1 Cannon) in attack VS (1 Rider and 1 Cannon) in defense ==> [2, 0, 1, 0, 1, 1]
     * @return dead [6] = [ AttackSoldier, AttackRider, AttackCannon, DefenseSoldier, DefenseRider, DefenseCannon ] (int [])
     * TODO : Add war animation !!!!!!!! because it's cool
     */
    public static int[] Hajime(int [] fighters) {

        // Armies ready, attacking side and defending side
        // TreeMap naturally sorts the keys in ascending order. "Collections.reverseOrder()" reverse that order to put the most powerful unit first in the list.
        TreeMap<Double, String> attack = new TreeMap<>(Collections.reverseOrder());
        TreeMap<Double, String> defense = new TreeMap<>(Collections.reverseOrder());

        // Scoreboard on casualties
        // Same as fighters[], dead[] follows dead unit in such fashion : [ AttackSoldier, AttackRider, AttackCannon, DefenseSoldier, DefenseRider, DefenseCannon ]
        int dead[] = new int[6];


        // Sample for territory methods
        Soldier soldier = new Soldier();
        Rider rider = new Rider();
        Cannon cannon = new Cannon();

        // Dice roll
        for (int role = 0; role < 6; role++) {
            while (fighters[role] > 0 ) {
                switch(role) {
                    case 0:
                        attack.put(Myfunction.random(soldier.getMin_power(), soldier.getMax_power()) + soldier.getPriorityATT(), "Soldier");
                        break;
                    case 1:
                        attack.put(Myfunction.random(rider.getMin_power(), rider.getMax_power()) + rider.getPriorityATT(), "Rider");
                        break;
                    case 2:
                        attack.put(Myfunction.random(cannon.getMin_power(), cannon.getMax_power()) + cannon.getPriorityATT(), "Cannon");
                        break;
                    case 3:
                        defense.put(Myfunction.random(soldier.getMin_power(), soldier.getMax_power()) + soldier.getPriorityATT(), "Soldier");
                        break;
                    case 4:
                        defense.put( Myfunction.random(rider.getMin_power(), rider.getMax_power()) + rider.getPriorityATT(), "Rider");
                        break;
                    case 5:
                        defense.put(Myfunction.random(cannon.getMin_power(), cannon.getMax_power()) + cannon.getPriorityATT(), "Cannon");
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


    /**
     * Remove dead units from both territories (to be used right after the "Hajime" function").
     * @param dead [6] = [ AttackSoldier, AttackRider, AttackCannon, DefenseSoldier, DefenseRider, DefenseCannon ] (int [])
     * @param ennemy_land (Territory)
     * TODO : Add GUY unit death.
     */
    public void TombRaider(int [] dead, Territory ennemy_land) {
        for (int role = 0; role < 6; role++) {
            while (dead[role] > 0 ) {
                switch(role) {
                    case 0:
                        this.army_soldiers.remove(1);
                        break;
                    case 1:
                        this.army_riders.remove(1);
                        break;
                    case 2:
                        this.army_cannons.remove(1);
                        break;
                    case 3:
                        ennemy_land.army_soldiers.remove(1);
                        break;
                    case 4:
                        ennemy_land.army_riders.remove(1);
                        break;
                    case 5:
                        ennemy_land.army_cannons.remove(1);
                        break;
                }
                dead[role]--;
            }
        }
    }


    /**
     * Adds new units to this territory.
     * @param babies = [Soldier, Rider, Cannon] (int [])
     * TODO : Add GUI unit movement.
     */
    private void UncleBenNeedsYou(int[] babies) {
        for (int role = 0; role < 3; role++) {
            while (babies[role] > 0 ) {
                switch(role) {
                    case 0:
                        Soldier baby_soldier = new Soldier();
                        this.army_soldiers.add(baby_soldier);
                        break;
                    case 1:
                        Rider baby_rider = new Rider();
                        this.army_riders.add(baby_rider);
                        break;
                    case 2:
                        Cannon baby_cannon = new Cannon();
                        this.army_cannons.add(baby_cannon);
                        break;
                }
                babies[role]--;
            }
        }
    }


    /**
     * Move units between adjacent territories.
     * @param prophets = [Soldier, Rider, Cannon] (int [])
     * @param promised_land (Territory)
     * @return true if the transfer of units has been made, false otherwise.
     * TODO : if (return false), add GUI error message that the player can't move its units.
     */
    public boolean MoveYourAss(int [] prophets, Territory promised_land) {
        for (int role = 0; role < 3; role++) {
            while (prophets[role] > 0 ) {
                switch(role) {
                    case 0:
                        if (CheckImmigrant("Soldier")) {
                            Soldier soldier = this.army_soldiers.get(1);
                            this.army_soldiers.remove(soldier);
                            promised_land.army_soldiers.add(soldier);
                            soldier.addCpt();
                        }
                        else { return false; }
                        break;
                    case 1:
                        if (CheckImmigrant("Rider")) {
                            Rider rider = this.army_riders.get(1);
                            this.army_riders.remove(1);
                            promised_land.army_riders.add(rider);
                            rider.addCpt();
                        }
                        else { return false; }
                        break;
                    case 2:
                        if (CheckImmigrant("Cannon")) {
                            Cannon cannon = this.army_cannons.get(1);
                            this.army_cannons.remove(1);
                            promised_land.army_cannons.add(cannon);
                            cannon.addCpt();
                        }
                        else { return false; }
                        break;
                }
                prophets[role]--;
            }
        }
        return true;
    }


    /**
     * Check if moving units is possible
     * @param immigrant (String)
     * @return true is unit can be transfered, false otherwise.
     */
    private boolean CheckImmigrant(String immigrant) {
        switch(immigrant){
            case "Soldier":
                if ((this.army_soldiers.get(1) != null) &&
                     this.army_soldiers.get(1).getCpt() != this.army_soldiers.get(1).getMpt()) {
                    return false;
                }
                break;
            case "Rider":
                if ((this.army_riders.get(1) != null) &&
                     this.army_riders.get(1).getCpt() != this.army_riders.get(1).getMpt()) {
                    return false;
                }
                break;
            case "Cannon":
                if ((this.army_cannons.get(1) != null) &&
                     this.army_cannons.get(1).getCpt() != this.army_cannons.get(1).getMpt()) {
                    return false;
                }
                break;
        }
        return true;
    }


    /**
     * Automatically pick defensive units.
     * @param kingdom (Territory)
     * @return royal_guards [3] = [Soldier, Rider, Cannon] (int [])
     * TODO : Add GUI chosen units.
     */
    public static int [] ProtectTheQueen(Territory kingdom) {
        // Prepare the number of defending unit
        int [] royal_guards = new int[3];
        int call = 0;

        // Check the available units
        int plebe = kingdom.army_cannons.size() + kingdom.army_riders.size() + kingdom.army_soldiers.size();

        // Set aside units that were already chosen
        int called_soldier = 0;
        int called_rider = 0;
        int called_cannon = 0;

        // Pick defending units according to their defense priority
        while ((call < 2) || (call < plebe)) {
            if (kingdom.army_soldiers.get(called_soldier) != null) {
                royal_guards[0]++;
                called_soldier++;
                call++;
            }
            else if (kingdom.army_cannons.get(called_cannon) != null) {
                royal_guards[2]++;
                called_cannon++;
                call++;
            }
            else if (kingdom.army_riders.get(called_rider) != null) {
                royal_guards[1]++;
                called_rider++;
                call++;
            }
        }

        return (royal_guards);
    }
}