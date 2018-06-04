import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.*;


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
    private Region region;
    private ArrayList<Soldier> army_soldiers = new ArrayList<>();
    private ArrayList<Rider> army_riders = new ArrayList<>();
    private ArrayList<Cannon> army_cannons = new ArrayList<>();

    private ArrayList<ArrayList<Integer>> coordinatesXY = new ArrayList<>();

    private int[] soldierCoordinates = {0,0};
    private int[] ridersCoordinates = {0,0};
    private int[] cannonsCoordinates = {0,0};


    //
    // Default constructor /////////////////////////////////////////////////////////////////////////////////////////////
    //
    public Territory(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public int[] getSoldierCoordinates() {
        return soldierCoordinates;
    }

    public void setSoldierCoordinates(int x, int y) {
        this.soldierCoordinates[0] = x;
        this.soldierCoordinates[1] = y;
    }

    public int[] getRidersCoordinates() {
        return ridersCoordinates;
    }

    public void setRidersCoordinates(int x, int y) {
        this.ridersCoordinates[0] = x;
        this.ridersCoordinates[1] = y;
    }

    public int[] getCannonsCoordinates() {
        return cannonsCoordinates;
    }

    public void setCannonsCoordinates(int x, int y) {
        this.cannonsCoordinates[0] = x;
        this.cannonsCoordinates[1] = y;
    }

    public ArrayList<ArrayList<Integer>> getCoordinatesXY() {
        return coordinatesXY;
    }

    public void addCoordinatesXY(int x, int y) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(x);
        temp.add(y);
        this.coordinatesXY.add(temp);
    }

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        if (this.player != null) {
            this.player.removeTerritory(this);
        }
        player.addTerritory(this);
        this.player = player;
    }

    public ArrayList<Soldier> getArmy_soldiers() {
        return army_soldiers;
    }

    public void setArmy_soldiers(ArrayList<Soldier> army_soldiers) {
        this.army_soldiers = army_soldiers;
    }

    public ArrayList<Rider> getArmy_riders() {
        return army_riders;
    }

    public void setArmy_riders(ArrayList<Rider> army_riders) {
        this.army_riders = army_riders;
    }

    public ArrayList<Cannon> getArmy_cannons() {
        return army_cannons;
    }

    public void setArmy_cannons(ArrayList<Cannon> army_cannons) {
        this.army_cannons = army_cannons;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
        this.region.addInAllTerritories(this);
    }

    //
    // Methods /////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    /**
     * Set the adjacents list adding the territory in it.
     * @param territory (Territory)
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
        Myfunction mf = new Myfunction();
        for (int role = 0; role < 6; role++) {
            while (fighters[role] > 0 ) {
                switch(role) {
                    case 0:
                        attack.put(mf.random(soldier.getMin_power(), soldier.getMax_power()) + soldier.getPriorityATT(), "Soldier");
                        break;
                    case 1:
                        attack.put(mf.random(rider.getMin_power(), rider.getMax_power()) + rider.getPriorityATT(), "Rider");
                        break;
                    case 2:
                        attack.put(mf.random(cannon.getMin_power(), cannon.getMax_power()) + cannon.getPriorityATT(), "Cannon");
                        break;
                    case 3:
                        defense.put(mf.random(soldier.getMin_power(), soldier.getMax_power()) + soldier.getPriorityATT(), "Soldier");
                        break;
                    case 4:
                        defense.put(mf.random(rider.getMin_power(), rider.getMax_power()) + rider.getPriorityATT(), "Rider");
                        break;
                    case 5:
                        defense.put(mf.random(cannon.getMin_power(), cannon.getMax_power()) + cannon.getPriorityATT(), "Cannon");
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
    public void KillBill(int [] dead, Territory ennemy_land) {
        for (int role = 0; role < 6; role++) {
            while (dead[role] > 0 ) {
                switch(role) {
                    case 0:
                        this.army_soldiers.remove(0);
                        break;
                    case 1:
                        this.army_riders.remove(0);
                        break;
                    case 2:
                        this.army_cannons.remove(0);
                        break;
                    case 3:
                        ennemy_land.army_soldiers.remove(0);
                        break;
                    case 4:
                        ennemy_land.army_riders.remove(0);
                        break;
                    case 5:
                        ennemy_land.army_cannons.remove(0);
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
    public void UncleBenNeedsYou(int[] babies) {
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
     */
    public void MoveYourAss(int [] prophets, Territory promised_land) {
        for (int role = 0; role < 3; role++) {
            while (prophets[role] > 0 ) {
                switch(role) {
                    case 0:
                            Soldier soldier = this.army_soldiers.get(1);
                            this.army_soldiers.remove(soldier);
                            promised_land.army_soldiers.add(soldier);
                            soldier.addCpt();
                        break;
                    case 1:
                            Rider rider = this.army_riders.get(1);
                            this.army_riders.remove(1);
                            promised_land.army_riders.add(rider);   
                            rider.addCpt();
                        break;
                    case 2:
                            Cannon cannon = this.army_cannons.get(1);
                            this.army_cannons.remove(1);
                            promised_land.army_cannons.add(cannon);
                            cannon.addCpt();
                        break;
                }
                prophets[role]--;
            }
        }
    }


    /**
     * Check if moving units is possible
     * @param immigrant (String)
     * @return true is unit can be transfered, false otherwise.
     */
    private boolean CheckImmigrant(String immigrant) {
        switch(immigrant){
            case "Soldier":
                if ((this.army_soldiers.get(1) == null) ||
                    (this.army_soldiers.get(1).getCpt() == this.army_soldiers.get(1).getMpt())) {
                    return false;
                }
                break;
            case "Rider":
                if ((this.army_riders.get(1) == null) ||
                    (this.army_riders.get(1).getCpt() == this.army_riders.get(1).getMpt())) {
                    return false;
                }
                break;
            case "Cannon":
                if ((this.army_cannons.get(1) == null) ||
                    (this.army_cannons.get(1).getCpt() == this.army_cannons.get(1).getMpt())) {
                    return false;
                }
                break;
        }
        return true;
    }


    /**
     * Automatically pick defensive units, to be used right before Hajime
     * @return royal_guards [3] = [Soldier, Rider, Cannon] (int [])
     */
    public int [] ProtectTheQueen() {
        // Prepare the number of defending unit
        int [] royal_guards = new int[3];
        int call = 0;

        // Check the available units
        int plebe = this.army_cannons.size() + this.army_riders.size() + this.army_soldiers.size();

        // Set aside units that were already chosen
        int called_soldier = 0;
        int called_rider = 0;
        int called_cannon = 0;

        // Pick defending units according to their defense priority
        while ((call < 2) && (call < plebe)) {
            if (this.army_soldiers.size() > called_soldier) {
                royal_guards[0]++;
                called_soldier++;
                call++;
            }
            else if (this.army_cannons.size() > called_cannon) {
                royal_guards[2]++;
                called_cannon++;
                call++;
            }
            else if (this.army_riders.size() > called_rider) {
                royal_guards[1]++;
                called_rider++;
                call++;
            }
        }

        return (royal_guards);
    }

    /**
     * Join attack and defense array into one, to be used before Hajime.
     * @param horde (int [3] = [AttackSoldier, AttackRider, AttackCannon] (int [])
     * @param alliance (int [3] = [DefenseSoldier, DefenseRider, DefenseCannon] (int [])
     * @return fighters [6] = [AttackSoldier, AttackRider, AttackCannon, DefenseSoldier, DefenseRider, DefenseCannon] (int [])
     */
    public int [] MoshPit(int [] horde, int [] alliance) {
        int [] fighters = new int[6];
        for (int i=0; i<3; i++) {
            fighters[i] = horde[i];
        }
        for (int i=0; i<3; i++) {
            fighters[i+3] = alliance[i];
        }
        return(fighters);
    }

    /**
     * Combine functions for the full fight
     * @param heroes [6] = [ AttackSoldier, AttackRider, AttackCannon, DefenseSoldier, DefenseRider, DefenseCannon ] (int [])
     * @param noxus (Territory)
     * TODO : Add GUI full fight
     */
    public void AllMightO(int [] heroes, Territory noxus) {
        int [] villains = noxus.ProtectTheQueen();
        int [] champions = MoshPit(heroes, villains);
        int [] graveyard = Hajime(champions);
        this.KillBill(graveyard, noxus);
        int [] survivors = HungerGames(champions, graveyard);
        this.MyNewHome(survivors, noxus);
    }

    /**
     * Check if the attacking team doesn't go beyond 3 units
     * @param monsters (int [3] = [AttackSoldier, AttackRider, AttackCannon] (int [])
     * @return pass (boolean)
     */
    public boolean Gandalf (int [] monsters) {
        int power = monsters[0] + monsters [1] + monsters [2];

        // Too many units
        if (power > 3) {
            System.out.println("YOU SHALL NOT PASS");
            return(false);
        }

        // Enough units
        else {
            System.out.println("cool bro");
            return(true);
        }
    }

    /**
     * Conquer a territory after a fight if it's empty
     * @param zetsus [3] = [Soldier, Rider, Cannon] (int [])
     * @param konoha (Territoy)
     */
    public void MyNewHome(int [] zetsus, Territory konoha) {
        int shinobi = konoha.getArmy_riders().size() + konoha.getArmy_riders().size() + konoha.getArmy_cannons().size();
        if (shinobi == 0) {
            konoha.setPlayer(this.getPlayer());
            this.MoveYourAss(zetsus, konoha);
        }
    }

    /**
     * Check the survivors of a fight
     * @param tributes [6] = [ AttackSoldier, AttackRider, AttackCannon, DefenseSoldier, DefenseRider, DefenseCannon ] (int [])
     * @param losers [3] = [Soldier, Rider, Cannon]
     * @return winners [3] = [Soldier,Rider, Cannon)
     */
    public int [] HungerGames (int [] tributes, int [] losers) {
        int [] winners = new int[3];

        for (int i=0; i<winners.length; i++) {
            winners[i] = tributes[i] - losers[i];
        }

        return winners;
    }
}