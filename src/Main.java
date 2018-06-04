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



        while (!match.getVictory()) {

            for (Player player : google.getAllPlayers()) {
                fenetre.setDashboardPanelRelativeTo(player, " C'est ton tour !", 0);
                System.out.println("C'est ton tour " + player.getName());


                while (!fenetre.isFinDuTour()) {


                    while (!fenetre.isFinDesAttaques()) {
                        System.out.println("Ce n'est pas la fin des attaques ! Héhé");
                        boolean notYouTerritory = true;
                        fenetre.setWaitForClick(true);


                        while (fenetre.isWaitForClick() && notYouTerritory) {
                            if(fenetre.isFinDesAttaques()) {
                                System.out.println("Fin des attaques");
                                fenetre.setDashboardPanelRelativeTo(player, "", 0);
                                break;
                            }
                            try {
                                Thread.sleep(10);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (fenetre.getTerritoryChosenOne() != null) {
                                if (fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                                        fenetre.getTerritoryChosenOne().getArmy_soldiers().size() > 1) {
                                    // A Changer en fonction du nombre de soldiers, riders et cannons
                                    System.out.println("I choose " + fenetre.getTerritoryChosenOne().getName());
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
                        fenetre.setTerritoryChosenOne(null);

                        while (fenetre.isWaitForClick() && notYouTerritory) {
                            if(fenetre.isFinDesAttaques()) {
                                System.out.println("Fin des attaques");
                                fenetre.setDashboardPanelRelativeTo(player, "", 0);
                                break;
                            }
                            try {
                                Thread.sleep(10);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (fenetre.getTerritoryChosenOne() != null) {
                                if (!fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                                    theChosenOne.getAdjacents().contains(fenetre.getTerritoryChosenOne())) {
                                    // Attaques

                                    Territory theChosenTwo = fenetre.getTerritoryChosenOne();
                                    System.out.println("C'est l'heure du du-du-du-du-dueel " + theChosenTwo.getName());
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
                                    System.out.println("Choose my own territory");
                                    break;
                                } else {
                                    notYouTerritory = true;
                                }
                            }
                        }
                        fenetre.setUnitsOnMap();
                    }
                    fenetre.setTerritoryChosenOne(null);


                    /*--------DEPLACEMENT----------*/
                    match.HiraishinNoJutsu(fenetre, player);

                }
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
