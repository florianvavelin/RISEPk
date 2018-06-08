import java.awt.*;
import java.lang.*;

public class Robot extends Player {

    public Robot(String name, Color color, Boolean IsAnIa) {
        super(name, color, IsAnIa);
    }

    public  void robot_recruit (int recruits){
        while (recruits > 0) {
            Myfunction mf = new Myfunction();
            int list_size = this.getTerritories().size();
            int random_number = mf.random(0, list_size - 1);

            Territory territory = this.getTerritories().get(random_number);
            int[] babies = {1, 0, 0};
            territory.UncleBenNeedsYou(babies);
            recruits--;
            }
    }

    public int territory_population (Territory territory) {
        int population = territory.getArmy_soldiers().size() + territory.getArmy_riders().size() + territory.getArmy_cannons().size();
        return population;
    }

    public boolean teritory_ennemies (Territory territory) {
        int counter = 0;
        for (Territory land : territory.getAdjacents()) {
            if (land.)
        }
    }

    public void robot_attack () {
        for (Territory territory : this.getTerritories()) {
            if (territory_population(territory) > 1) {

            }
        }
    }


    public void robot_move () {

    }
}
