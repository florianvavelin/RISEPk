import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;
import java.awt.image.*;

public class Myfunction {

    public int random(int min, int max) {
        int res = ThreadLocalRandom.current().nextInt(min, max + 1);
        return res;
    }

}
