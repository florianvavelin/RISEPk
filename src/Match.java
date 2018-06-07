import javax.swing.*;
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

    public void HiraishinNoJutsu(Fenetre fenetre, Player player, Territory theChosenOnePast) {
        fenetre.setDashboardPanelRelativeTo(player, "Phase de déplacement", 0);
        while (!fenetre.isFinDuTour()) {
            boolean notYouTerritory = true;
            fenetre.setWaitForClick(true);
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
                    Territory theChosenOne = fenetre.getTerritoryChosenOne();
                    System.out.println("theChosenOne.getName() = "+ theChosenOne.getName());
                    if (!theChosenOne.equals(theChosenOnePast) &&
                            theChosenOne.getPlayer().equals(player) &&
                            theChosenOnePast.getAdjacents().contains(theChosenOne)) {
                        // click on a territory different from the previous one and if it is our own territory
                        JComboBox[] IMadeMyChoice = fenetre.getMyChoice();
                        int[] wantSomeHelp = new int[IMadeMyChoice.length];
                        int armyChosen = 0, armyAllowed = 0;
                        for (int i = 0; i < IMadeMyChoice.length; i++) {
                            wantSomeHelp[i] = IMadeMyChoice[i].getSelectedIndex();
                            armyAllowed += IMadeMyChoice[i].getItemCount();
                            armyChosen += wantSomeHelp[i];
                        }
                        armyAllowed -= IMadeMyChoice.length; // remove the 0 rows at the beginning
                        System.out.println("armyChosen = " + armyChosen);
                        System.out.println("armyAllowed = " + armyAllowed);
                        if (armyAllowed - armyChosen >= 1) {
                            theChosenOnePast.MoveYourAss(wantSomeHelp, theChosenOne);
                            fenetre.setTerritoryChosenOne(theChosenOnePast);
                            fenetre.setDashboardPanelRelativeTo(player, "Phase de déplacement",0);
                            fenetre.setUnitsOnMap();
                            theChosenOnePast = new Territory("", Color.white);
                        }
                        break;
                    } else if (theChosenOne.getPlayer().equals(player) &&
                            (theChosenOne.getArmy_soldiers().size() + theChosenOne.getArmy_riders().size() +
                                    theChosenOne.getArmy_cannons().size() > 1)) {
                        // click on one of our own territories
                        theChosenOnePast = fenetre.getTerritoryChosenOne();
                        System.out.println("theChosenOnePast.getName() = " + theChosenOnePast.getName());
                        fenetre.setDashboardPanelRelativeTo(player, "Phase de déplacement", 0);
                        fenetre.setTerritoryChosenOne(null);
                        break;
                    } else {
                        notYouTerritory = true;
                    }
                }
                fenetre.setTerritoryChosenOne(null);
            }
            fenetre.setUnitsOnMap();
            fenetre.setTerritoryChosenOne(null);
        }
    }
}