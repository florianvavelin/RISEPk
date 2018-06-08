import javax.swing.*;
import java.awt.*;


public class Match {

    private boolean victory;

    /**
     * Default constructor
     */
    public Match(boolean victory) {
        this.victory = victory;
    }


    /**
     * Default getters and setters
     */
    public boolean getVictory() {
        return this.victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    ////////////////////////////////////// CUSTOM METHODS /////////////////////////////////////////////////////

    /**
     * - initialize : Let a player place his first soldiers of the game.
     * - HiraishinNoJutsu : Handles the mouvement phase
     */

    /**
     * Let a player place his first soldiers of the game.
     *
     * @param fenetre (Fenetre) - The environment in which the game takes place
     * @param google (Google) - The data on which the game relies
     */
    public void initialize(Fenetre fenetre, Google google) {
        // Number of beginning soldiers for each player, according to the number of players
        int army = 50 - 5 * google.getAllPlayers().size();

        // Placing soldiers
        for (Player player : google.getAllPlayers()) {
            // Number of soldiers the player gets to place
            int toPlace = army - player.getTerritories().size();

            // While there are still soldiers to place
            while (toPlace > 0) {
                // Set the dashboard to enrolment phase
                fenetre.setDashboardPanelRelativeTo(player, "placement", toPlace);

                // Waiting for a click on a correct territory
                boolean notYouTerritory = true;
                fenetre.setWaitForClick(true);
                // Waiting for a click
                while (fenetre.isWaitForClick() && notYouTerritory) {
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // Check if the click locates a territory
                    if (fenetre.getTerritoryChosenOne() != null) {
                        // Check if the territory is owned by the current player
                        if (fenetre.getTerritoryChosenOne().getPlayer().equals(player)) {
                            fenetre.setWaitForClick(false);
                            notYouTerritory = false;
                        } else { // The click doesn't locate a territory
                            notYouTerritory = true;
                        }
                    }
                }

                // Put a soldier on the clicked territory
                Territory theChosenOne = fenetre.getTerritoryChosenOne();
                int[] babies = {1, 0, 0};
                theChosenOne.UncleBenNeedsYou(babies);

                fenetre.setUnitsOnMap();

                fenetre.setTerritoryChosenOne(null);
                toPlace--;
            }
        }
    }


    /**
     * Handles the mouvement phase.
     *
     * @param fenetre (Fenetre) - The environment in which the game takes place
     * @param player (Player)
     * @param theChosenOnePast (Territory) - The starting territory for unit mouvement
     */
    public void HiraishinNoJutsu(Fenetre fenetre, Player player, Territory theChosenOnePast) {
        // Set the dashboard to mouvement phase
        fenetre.setDashboardPanelRelativeTo(player, "Phase de déplacement", 0);

        // While the turn is ongoing
        while (!fenetre.isFinDuTour()) {

            // Waiting for a click on a correct territory
            boolean notYouTerritory = true;
            fenetre.setWaitForClick(true);
            // Waiting for a click
            while (fenetre.isWaitForClick() && notYouTerritory) {
                // Check if the click ends the turn
                if (fenetre.isFinDuTour()) {
                    System.out.println("Fin du tour");
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Check if the click locates a territory
                if (fenetre.getTerritoryChosenOne() != null) {
                    Territory theChosenOne = fenetre.getTerritoryChosenOne();
                    System.out.println("theChosenOne.getName() = "+ theChosenOne.getName());
                    // Check if the chosen ending territory can be set as ending territory
                    if (!theChosenOne.equals(theChosenOnePast) &&                       // Different from the starting territory
                            theChosenOne.getPlayer().equals(player) &&                  // Owned by the current player
                            theChosenOnePast.getAdjacents().contains(theChosenOne)) {   // Adjacent to the starting territory

                        // Analyze the amount of chosen units among an allowed amount
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

                        // Check if 1 unit will stay on the starting territory
                        if (armyAllowed - armyChosen >= 1) {
                            // Move units
                            theChosenOnePast.MoveYourAss(wantSomeHelp, theChosenOne);
                            fenetre.setTerritoryChosenOne(theChosenOnePast);
                            // Update the GUI
                            fenetre.setDashboardPanelRelativeTo(player, "Phase de déplacement",0);
                            fenetre.setUnitsOnMap();
                            theChosenOnePast = new Territory("", Color.white);
                        }
                        break;
                    } else if (theChosenOne.getPlayer().equals(player) &&                                           // Owned by the current player
                            (theChosenOne.getArmy_soldiers().size() + theChosenOne.getArmy_riders().size() +
                                    theChosenOne.getArmy_cannons().size() > 1)) {                                   // More than 1 owned unit
                        // Set the clicked territory as starting territory
                        theChosenOnePast = fenetre.getTerritoryChosenOne();
                        System.out.println("theChosenOnePast.getName() = " + theChosenOnePast.getName());
                        fenetre.setDashboardPanelRelativeTo(player, "Phase de déplacement", 0);
                        fenetre.setTerritoryChosenOne(null);
                        break;
                    } else {        // Clicked territory is unusable
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