/**
 * Soldiers are the most basic units of the game.
 * Their characteristics are the following :
 *  - Cost : 3
 *  - Power : 2 - 7
 *  - Attack priority : 1
 *  - Defense priority : 3
 *  - Mouvement per turn : 3
 */
public class Soldier extends Unit {

    /**
     * Default constructor
     */
    public Soldier() {
        super(1, 1,6, 0.2,0.3,2);
    }

}