/**
 * Units are fundamental elements of the game that are used to occupy territories and battle.
 * They can come in different types, providing different kind of uses according to their own characteristics.
 * The different characteristics are as follows :
 *  - Cost : The amount of soldiers needed for trade in order to get such unit.
 *  - Power : The dice range allowed during dice throws to get a unit's final battle power.
 *  - Attack priority : The rank which rules units order in an attack set when at least 2 of them got the same final battle power.
 *  - Defense priority : The rank which rules units order in a defense set when at least 2 of them got the same final battle power.
 *                       Also checks which units are committed to defend a territory that owns more than 2 units.
 *  - Mouvement per turn : The number of allowed travels between adjacent territories of such unit during a player's turn.
 */
public abstract class Unit {

    private int cost;
    private int min_power;
    private int max_power;
    private double priorityATT;
    private double priorityDEF;
    private int mpt;

    /**
     * cpt, aka Counter per turn
     *
     * Variable needed to save the current mouvement points that are already used during a player's turn, and therefore
     * initialized to 0 at every turn.
     */
    private int cpt = 0;


    /**
     * Default constructor
     */
    public Unit(int cost, int min_power, int max_power, double priorityATT, double priorityDEF, int mpt) {
        this.cost = cost;
        this.min_power = min_power;
        this.max_power = max_power;
        this.priorityATT = priorityATT;
        this.priorityDEF = priorityDEF;
        this.mpt = mpt;
    }


    /**
     * Default getters and setters
     * @return
     */
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getMin_power() {
        return min_power;
    }

    public void setMin_power(int min_power) {
        this.min_power = min_power;
    }

    public int getMax_power() {
        return max_power;
    }

    public void setMax_power(int max_power) {
        this.max_power = max_power;
    }

    public double getPriorityATT() {
        return priorityATT;
    }

    public void setPriorityATT(double priorityATT) {
        this.priorityATT = priorityATT;
    }

    public double getPriorityDEF() {
        return priorityDEF;
    }

    public void setPriorityDEF(double priorityDEF) {
        this.priorityDEF = priorityDEF;
    }

    public int getMpt() {
        return mpt;
    }

    public void setMpt(int mpt) {
        this.mpt = mpt;
    }

    public int getCpt() {
        return cpt;
    }

    public void setCpt(int cpt) {
        this.cpt = cpt;
    }

    public void addCpt() {
        this.cpt++;
    }
}