import java.awt.*;
import java.util.*;


public class Match {

    private boolean victory;

    /**
     * Default constructor
     */
    public Match(boolean victory) {
        this.victory = victory;
    }


    /**
     * @param territory
     * @param player
     * @param mission
     * @param region
     */
    public void setVictory(Territory territory, Player player, Mission mission, Region region) {
    }

    /**
     * @return
     */
    public boolean getVictory() {
        return this.victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    /**
     * For each player, calculate the soldiers he has to place.
     * Then wait for the player to choose one of his territory.
     * Place a soldier in it.
     *
     * @param fenetre
     * @param google
     */
    public void initialize(Fenetre fenetre, Google google) {
        int army = 50 - 5 * google.getAllPlayers().size(); // army at the beginning of the game
        for (Player player : google.getAllPlayers()) {
            int toPlace = army - player.getTerritories().size(); // number of soldiers to place
            while (toPlace > 0) {
                fenetre.setDashboardPanelRelativeTo(player, "placement", toPlace);
                boolean notYouTerritory = true;
                fenetre.setWaitForClick(true);
                while (fenetre.isWaitForClick() && notYouTerritory) {
                    // we wait for a click
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (fenetre.getTerritoryChosenOne() != null) {
                        if (fenetre.getTerritoryChosenOne().getPlayer().equals(player)) {
                            // my territory
                            fenetre.setWaitForClick(false); // don't wait for a click anymore
                            notYouTerritory = false;
                        } else {
                            notYouTerritory = true;
                        }
                    }
                }
                Territory theChosenOne = fenetre.getTerritoryChosenOne(); // get the territory we clicked on
                int[] babies = {1, 0, 0};
                theChosenOne.UncleBenNeedsYou(babies); // put a soldier in the territory
                fenetre.setUnitsOnMap(); // refresh the map
                fenetre.setTerritoryChosenOne(null); // set the territory chosen by default to null
                toPlace--; // a place has been occupied
            }
        }
    }

    public void HiraishinNoJutsu(Fenetre fenetre, Player player) {
        Territory theChosenOnePast = fenetre.getTerritoryChosenOne();
        boolean notYouTerritory = true;
        fenetre.setWaitForClick(true);
        fenetre.setTerritoryChosenOne(null);
        while (fenetre.isWaitForClick() && notYouTerritory) {
            if (fenetre.isFinDuTour()) {
                System.out.println("Fin du tour");
                break;
            }
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (fenetre.getTerritoryChosenOne() != null) {
                if (theChosenOnePast != null &&
                        fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                        fenetre.getTerritoryChosenOne().getAdjacents().contains(theChosenOnePast)) {
                    // click on a territory different from the previous one and if it is our own territory
                    Territory theChosenOne = fenetre.getTerritoryChosenOne();
                    int[] wantSomeHelp = {theChosenOnePast.getArmy_soldiers().size() - 1, 0, 0}; // change to what the user chooses
                    theChosenOnePast.MoveYourAss(wantSomeHelp, theChosenOne);
                    fenetre.setTerritoryChosenOne(null);
                    break;
                } else if (fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                        fenetre.getTerritoryChosenOne().getArmy_soldiers().size() > 1) {
                    /**
                     * TODO
                     * or get_Army_riders() > 1 or get_Army_cannons.size() > 1
                     */
                    // click on one of our own territories
                    theChosenOnePast = fenetre.getTerritoryChosenOne();
                    fenetre.setDashboardPanelRelativeTo(player, "Phase de déplacement", 0);
                    break;
                } else {
                    notYouTerritory = true;
                }
            }
        }
        fenetre.setUnitsOnMap();
    }
}