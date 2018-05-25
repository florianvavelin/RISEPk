
import java.util.*;

/**
 * 
 */
public class Mission {

    public String name;

    /**
     * Default constructor
     */
    public Mission(String name) {
        this.name = name;
    }



    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }


    String [] MissionName = {"Hitler", "Attila" ,"Staline", "Kim Jong Un", "Fidel Castro", "Omar Bongo"};
    String [] MissionText = {"Conquer Europe",
                                "Conquer Asia",
                                "Conquer North America",
                                "Destroy 2 players",
                                "Conquer South America",
                                "Conquer Africa"};
}