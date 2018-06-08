import java.util.concurrent.ThreadLocalRandom;

/**
 * This class act as a global interface for generic functions.
 */
public class Myfunction {

    ////////////////////////////////////// CUSTOM METHODS /////////////////////////////////////////////////////

    /**
     * - random : Computes a random integer in a given range.
     */

    /**
     * Computes a random integer in a given range.
     *
     * @param min (int) - Minimum value of range
     * @param max (int) - Maximum value of range
     * @return res (int) - Random chosen integer within the range
     */
    public int random(int min, int max) {
        int res = ThreadLocalRandom.current().nextInt(min, max + 1);
        return res;
    }

}
