import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        while (menu.getFen() == null) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Fenetre fenetre = menu.getFen();
        Google google = fenetre.getGoogle();
        Match match = new Match(false);
        String winner = "";


        match.initialize(fenetre, google);

        int guyCodeHarry = google.getAllPlayers().size();
        while (!match.getVictory()) {
            for (Player player : google.getAllPlayers()) {
                Robot test = new Robot("test", Color.white);
                if (player.getClass() != test.getClass()) {
                    if (guyCodeHarry <= 0) {
                        /**
                         * Renforts en début de tour
                         * guyCodeHarry is set to 2 if there are 2 players.
                         * At the beginning, after initialization, each player attacks and moves.
                         * Next turn of the player : he chooses new units to place.
                         */
                        int JingleBell = player.Christmas(); // number of units allowed (It's Christmas!)
                        System.out.println("JingleBell = " + JingleBell);
                        fenetre.setWhatUnit(0); // Set the choice to soldier at the beginning
                        while (JingleBell > 0) {
                            int choiceUnitPast = fenetre.getWhatUnit();
                            fenetre.setDashboardPanelRelativeTo(player, "Renforts", JingleBell);
                            int[] JingleBellUnits = player.MixTheJuice(JingleBell);
                            // calcul des max de chaque unité dans un array [Soldier, Rider, Cannon]
                            boolean notYouTerritory = true;
                            fenetre.setWaitForClick(true);
                            fenetre.setTerritoryChosenOne(null);
                            while (fenetre.isWaitForClick() && notYouTerritory) {
                                try {
                                    Thread.sleep(10);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (fenetre.getWhatUnit() != choiceUnitPast) {
                                    // if right click, change the unit (Soldier -> Rider -> Cannon -> Soldier -> ...)
                                    choiceUnitPast = fenetre.getWhatUnit();
                                    fenetre.setDashboardPanelRelativeTo(player, "Renforts", JingleBell);
                                }
                                if (fenetre.getTerritoryChosenOne() != null) {
                                    if (fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                                            JingleBellUnits[fenetre.getWhatUnit()] > 0) {
                                        // choose our own territory and the unit chosen can be set in the map
                                        Territory theChosenOne = fenetre.getTerritoryChosenOne();
                                        int[] unitToPlace = new int[3];
                                        unitToPlace[fenetre.getWhatUnit()] = 1;
                                        theChosenOne.UncleBenNeedsYou(unitToPlace);
                                        fenetre.setUnitsOnMap(); // Show it on the map
                                        int newJingleBell = JingleBell;
                                        switch (fenetre.getWhatUnit()) {
                                            case 0:
                                                newJingleBell -= (new Soldier().getCost());
                                                break;
                                            case 1:
                                                newJingleBell -= (new Rider().getCost());
                                                break;
                                            case 2:
                                                newJingleBell -= (new Cannon().getCost());
                                                break;
                                        }
                                        JingleBell = newJingleBell; // Set the new number of armies allowed
                                        break;
                                    } else {
                                        notYouTerritory = true;
                                    }
                                }
                            }
                        }
                    }
                    //fenetre.setDashboardPanelRelativeTo(player, " C'est ton tour !", 0);
                    System.out.println("C'est ton tour " + player.getName());
                    fenetre.setFinDesAttaques(false);
                    fenetre.setFinDuTour(false);


                    while (!fenetre.isFinDuTour()) {


                        Territory theChosenOnePast = new Territory("", Color.white);
                        fenetre.setDashboardPanelRelativeTo(player, "", 0);
                        while (!fenetre.isFinDesAttaques()) {
                            System.out.println("Ce n'est pas la fin des attaques ! Héhé");

                            boolean notYouTerritory = true;
                            fenetre.setWaitForClick(true);
                            while (fenetre.isWaitForClick() && notYouTerritory) {
                                if (fenetre.isFinDesAttaques()) {
                                    System.out.println("Fin des attaques");
                                    fenetre.setDashboardPanelRelativeTo(player, "Phase de déplacement", 0);
                                    break;
                                }
                                try {
                                    Thread.sleep(5);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (fenetre.getTerritoryChosenOne() != null) {
                                    Territory theChosenOne = fenetre.getTerritoryChosenOne();
                                    if (!theChosenOne.equals(theChosenOnePast) &&
                                            !theChosenOne.getPlayer().equals(player) &&
                                            theChosenOnePast.getAdjacents().contains(theChosenOne)) {
                                        // click on a territory different from the previous one and if it is an opponent
                                        JComboBox[] IMadeMyChoice = fenetre.getMyChoice();
                                        int[] nani = new int[IMadeMyChoice.length];
                                        int armyChosen = 0, armyAllowed = 0;
                                        for (int i = 0; i < IMadeMyChoice.length; i++) {
                                            nani[i] = IMadeMyChoice[i].getSelectedIndex();
                                            armyAllowed += IMadeMyChoice[i].getItemCount();
                                            armyChosen += nani[i];
                                        }
                                        armyAllowed -= IMadeMyChoice.length; // remove the 0 rows at the beginning
                                        System.out.println("armyChosen = " + armyChosen);
                                        System.out.println("armyAllowed = " + armyAllowed);
                                        if (armyChosen <= 3 && armyAllowed - armyChosen >= 1) {
                                            theChosenOnePast.AllMightO(nani, theChosenOne);
                                            fenetre.setTerritoryChosenOne(theChosenOnePast);
                                            fenetre.setDashboardPanelRelativeTo(player, "Phase d'attaque", 0);
                                            fenetre.setUnitsOnMap();
                                        }
                                        break;

                                    } else if (theChosenOne.getPlayer().equals(player) &&
                                            (theChosenOne.getArmy_soldiers().size() +
                                                    theChosenOne.getArmy_riders().size() +
                                                    theChosenOne.getArmy_cannons().size() > 1)) {
                                        // click on its own territories
                                        theChosenOnePast = fenetre.getTerritoryChosenOne();
                                        fenetre.setDashboardPanelRelativeTo(player, "Phase d'attaque", 0);
                                        fenetre.setTerritoryChosenOne(null); // own territory
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


                        /*--------DEPLACEMENT----------*/
                        Territory theChosenOnePast2 = new Territory("", Color.white);
                        match.HiraishinNoJutsu(fenetre, player, theChosenOnePast2);

                    }
                    guyCodeHarry--;
                    fenetre.setTerritoryChosenOne(null);
                    fenetre.setFinDuTour(false);
                    System.out.println("Fin du tour pour " + player.getName());

                    if (player.getTerritories().size() == google.getTerritories().size()) {
                        match.setVictory(true);
                        winner = player.getName();
                        break;
                    }
                }
                else {
                    Robot robot = (Robot) player;
                    robot.robot_recruit(player.Christmas());
                    //robot.robot_attack();
                    robot.robot_move();
                    System.out.println(player.getName() + " : I finished you bitch");
                }
            }
        }

        Victory victory = new Victory(winner);
        System.out.println(winner + " wins.");
    }
}
