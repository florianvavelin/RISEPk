import java.awt.*;

public class Robot extends Player {

    public Robot(String name, Color color, Boolean IsAnIa) {
        super(name, color, IsAnIa);
    }

    public  void robot_recruit (int recruits){
        while (recruits > 0) {
            for (Territory territory : this.getTerritories()) {
                territory.UncleBenNeedsYou({1, 0, 0});
            }
        }

    }

    public void robot_attack (){

    }

    public void robot_move (){

    }
}
