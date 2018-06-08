import java.awt.*;
import java.lang.*;
import java.util.ArrayList;

public class Robot extends Player {

    public Robot(String name, Color color) {
        super(name, color);
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

    public ArrayList<Territory> territory_ennemies (Territory territory) {
        ArrayList<Territory> ennemies = new ArrayList<>();
        for (Territory land : territory.getAdjacents()) {
            if (!territory.getPlayer().equals(land.getPlayer())) {
                ennemies.add(land);
            }
        }
        return ennemies;
    }

    public void robot_attack () {
        for (Territory territory : this.getTerritories()) {
            while (territory_population(territory) > 1) {
                int ennemy_size = this.territory_ennemies(territory).size();
                if (ennemy_size > 0) {
                    Myfunction mf = new Myfunction();
                    int random_number = mf.random(0, ennemy_size - 1);

                    Territory real_ennemy = this.territory_ennemies(territory).get(random_number);

                    if(territory.getArmy_cannons().size() > 1) {
                        int[] attackers = {0, 0, 1};
                        territory.AllMightO(attackers, real_ennemy);
                    }

                     else if(territory.getArmy_soldiers().size() > 1) {
                        int[] attackers = {1, 0, 0};
                        territory.AllMightO(attackers, real_ennemy);
                    }

                    else if(territory.getArmy_soldiers().size() > 1) {
                        int[] attackers = {0, 1, 0};
                        territory.AllMightO(attackers, real_ennemy);
                    }
                }
            }
        }
    }


    public void robot_move () {
        for (Territory territory : this.getTerritories()) {
            if(this.territory_ennemies(territory).size() == 0) {
                Myfunction mf = new Myfunction();
                int random_number = mf.random(0, territory.getAdjacents().size() - 1);

                Territory real_ally = territory.getAdjacents().get(random_number);

                if(territory.getArmy_cannons().size() > 1) {
                    int[] movers = {0, 0, 1};
                    territory.MoveYourAss(movers, real_ally);
                }

                else if(territory.getArmy_soldiers().size() > 1) {
                    int[] movers = {1, 0, 0};
                    territory.MoveYourAss(movers, real_ally);
                }

                else if(territory.getArmy_soldiers().size() > 1) {
                    int[] movers = {0, 1, 0};
                    territory.MoveYourAss(movers, real_ally);
                }
            }
        }
    }
}
