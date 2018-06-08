/**
 * Cannons are an improved type of soldier unit.
 * Their characteristics are the following :
 *  - Cost : 7
 *  - Power : 4 - 9
 *  - Attack priority : 1
 *  - Defense priority : 2
 *  - Mouvement per turn : 1
 */
public class Cannon extends Unit {

    /**
     * Default constructor
     */
    public Cannon() {
        super(7, 4,9, 0.1,0.2,1 );
    }

}