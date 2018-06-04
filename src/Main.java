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
        int army = 50 - 5*google.getAllPlayers().size();
        for (Player player : google.getAllPlayers()) {
            int toPlace = army - player.getTerritories().size();
            while (toPlace > 0) {
                fenetre.setDashboardPanelRelativeTo(player, "placement", toPlace);
                boolean notYouTerritory = true;
                while (fenetre.isWaitForClick() && notYouTerritory) {

                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (fenetre.getTerritoryChosenOne() != null) {
                        if (fenetre.getTerritoryChosenOne().getPlayer().equals(player)) {

                            fenetre.setWaitForClick(false);
                            notYouTerritory = false;
                        } else {
                            notYouTerritory = true;
                        }
                    }
                }
                Territory theChosenOne = fenetre.getTerritoryChosenOne();
                int[] babies = {1,0,0};
                theChosenOne.UncleBenNeedsYou(babies);
                fenetre.setUnitsOnMap();
                fenetre.setWaitForClick(true);
                fenetre.setTerritoryChosenOne(null);
                toPlace--;
            }
        }
        while (!match.getVictory()) {

            for (Player player : google.getAllPlayers()) {
                fenetre.setDashboardPanelRelativeTo(player, " C'est ton tour !", 0);


                while (!fenetre.isFinDuTour()) {


                    while (!fenetre.isFinDesAttaques()) {
                        boolean notYouTerritory = true;
                        fenetre.setWaitForClick(true);


                        while (fenetre.isWaitForClick() && notYouTerritory) {
                            if(fenetre.isFinDesAttaques()) {
                                break;
                            }
                            try {
                                Thread.sleep(10);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (fenetre.getTerritoryChosenOne() != null) {
                                if (fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                                        fenetre.getTerritoryChosenOne().getArmy_soldiers().size() > 1) { //A Changer en fonction du nombre de soldats
                                    fenetre.setWaitForClick(false);
                                    notYouTerritory = false;
                                } else {
                                    notYouTerritory = true;
                                }
                            }
                        }

                        Territory theChosenOne = fenetre.getTerritoryChosenOne();
                        if (theChosenOne != null) {
                            fenetre.setDashboardPanelRelativeTo(player, theChosenOne.getName() + " against ", 0);
                        }

                        fenetre.setWaitForClick(true);
                        notYouTerritory = true;

                        while (fenetre.isWaitForClick() && notYouTerritory) {
                            if(fenetre.isFinDesAttaques()) {
                                break;
                            }
                            try {
                                Thread.sleep(10);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (fenetre.getTerritoryChosenOne() != null) {
                                if (!fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                                        theChosenOne.getAdjacents().contains(fenetre.getTerritoryChosenOne())) { //Attaques
                                        Territory theChosenTwo = fenetre.getTerritoryChosenOne();
                                        System.out.println(theChosenTwo.getName());
                                        if (theChosenOne.getArmy_soldiers().size() > 3) {
                                            int[] fighters = {3,0,0};
                                            System.out.println(fighters[0] + " soldats attaquants");
                                            theChosenOne.AllMightO(fighters, theChosenTwo);
                                        } else {
                                            int[] fighters = {theChosenOne.getArmy_soldiers().size()-1, 0, 0};
                                            System.out.println(fighters[0] + " soldats attaquants");
                                            theChosenOne.AllMightO(fighters, theChosenTwo);
                                        }
                                        fenetre.setWaitForClick(false);
                                        notYouTerritory = false;
                                } else if (fenetre.getTerritoryChosenOne().getPlayer().equals(player)) {
                                    break;
                                } else {
                                    notYouTerritory = true;
                                }
                            }
                        }

                        fenetre.setUnitsOnMap();
                    }

                }
                fenetre.setFinDuTour(false);

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
