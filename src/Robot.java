import java.awt.*;
import java.lang.*;

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
            territory.UncleBenNeedsYou({1, 0, 0)};
            recruits--;
            }
    }


    public void robot_move (){

    }
}
