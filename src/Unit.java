
import java.util.*;

/**
 * 
 */
public class Unit {

    private int cost;
    private int power;
    private int priorityATT;
    private int priorityDEF;

    /**
     * Movement per turn
     */
    private int mpt;



    /**
     * Default constructor
     */

    public Unit() {

    }

    public Unit(int cost, int power, int priorityATT, int priorityDEF, int mpt) {
        this.cost = cost;
        this.power = power;
        this.priorityATT = priorityATT;
        this.priorityDEF = priorityDEF;
        this.mpt = mpt;
    }





}