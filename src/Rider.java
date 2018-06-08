/**
 * Riders are an improved type of soldier unit.
 * Their characteristics are the following :
 *  - Cost : 3
 *  - Power : 2 - 7
 *  - Attack priority : 1
 *  - Defense priority : 3
 *  - Mouvement per turn : 3
 */
public class Rider extends Unit {

    /**
     * Default constructor
     */
    public Rider() {
        super(3, 2,7, 0.3,0.1,3);
    }

}