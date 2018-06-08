/**
 * Missions are achievements that the players need to complete in order to win the game.
 * By default, all players can win by getting every possible territory (and therefore, destroying every other player).
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
     * Default getters and setters
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


    /**
     * Naming ideas for the missions
     */
    String [] MissionName = {"Hitler", "Attila" ,"Staline", "Kim Jong Un", "Fidel Castro", "Omar Bongo"};
    String [] MissionText = {"Conquer Europe",
                                "Conquer Asia",
                                "Conquer North America",
                                "Destroy 2 players",
                                "Conquer South America",
                                "Conquer Africa"};
}