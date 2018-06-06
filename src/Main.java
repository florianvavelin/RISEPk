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
                if (guyCodeHarry <= 0) {
                    /**
                     * Renforts en début de tour
                      */
                    int JingleBell = player.Christmas(); // nombre de renforts
                    System.out.println("JingleBell = " + JingleBell);
                    fenetre.setWhatUnit(0);
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
                            if (fenetre.getWhatUnit() != choiceUnitPast ) {
                                // if right click, change the unit
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
                                    fenetre.setUnitsOnMap();
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
                                    JingleBell = newJingleBell;
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
                    fenetre.setDashboardPanelRelativeTo(player, "Phase d'attaque", 0);
                    while (!fenetre.isFinDesAttaques()) {
                        System.out.println("Ce n'est pas la fin des attaques ! Héhé");

                        boolean notYouTerritory = true;
                        fenetre.setWaitForClick(true);
                        while (fenetre.isWaitForClick() && notYouTerritory) {
                            if(fenetre.isFinDesAttaques()) {
                                System.out.println("Fin des attaques");
                                fenetre.setDashboardPanelRelativeTo(player, "Phase de déplacement", 0);
                                break;
                            }
                            try {
                                Thread.sleep(10);
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
                                    for (int i = 0; i < IMadeMyChoice.length; i++) {
                                        nani[i] = IMadeMyChoice[i].getSelectedIndex();
                                    }
                                    System.out.println("Soldats = " + nani[0]);
                                    System.out.println("Riders = " + nani[1]);
                                    System.out.println("Cannons = " + nani[2]);
                                    theChosenOnePast.AllMightO(nani, theChosenOne);
                                    fenetre.setUnitsOnMap();
                                    break;
                                } else if (theChosenOne.getPlayer().equals(player) &&
                                        (theChosenOne.getArmy_soldiers().size() +
                                                theChosenOne.getArmy_riders().size() +
                                                theChosenOne.getArmy_cannons().size() > 1 )) {
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
                    match.HiraishinNoJutsu(fenetre, player);

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
        }
        System.out.println(winner + " wins.");
    }
}
