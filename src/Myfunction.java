import java.util.concurrent.ThreadLocalRandom;

public class Myfunction {

    public static int random(int min, int max) {
        int res = ThreadLocalRandom.current().nextInt(min, max + 1);
        return res;
    }

}
