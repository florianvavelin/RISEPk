
import java.util.*;

/**
 * 
 */
public abstract class  Unit {

    private int cost;
    private int min_power;
    private int max_power;
    private double priorityATT;
    private double priorityDEF;

    /**
     * Movement per turn
     */
    private int mpt;


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





}