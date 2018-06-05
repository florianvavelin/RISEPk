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



        boolean guyCodeHarry = false;
        while (!match.getVictory()) {

            for (Player player : google.getAllPlayers()) {
                if (guyCodeHarry) {
                    // renforts en début de tour
                    // calcul du nombre d'unités à placer
                    // calcul des max de chaque unité
                    // "Vous avez X soldats à placer"
                    // Affiche le nombre de soldats, riders, cannons qu'on peut placer actuellement
                    // Sur la map :
                        // Clique droit pour changer d'unité (si on peut) et on affiche l'unité choisie dans le dashboard
                        // Clique gauche sur la map, place l'unité choisie
                        // recalcule les max
                } else {

                }
                fenetre.setDashboardPanelRelativeTo(player, " C'est ton tour !", 0);
                System.out.println("C'est ton tour " + player.getName());


                while (!fenetre.isFinDuTour()) {


                    Territory theChosenOnePast = new Territory("", Color.white);
                    while (!fenetre.isFinDesAttaques()) {
                        System.out.println("Ce n'est pas la fin des attaques ! Héhé");
                        //fenetre.setDashboardPanelRelativeTo(player, "", 0);


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
                                if (!fenetre.getTerritoryChosenOne().equals(theChosenOnePast) &&
                                        !fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                                        theChosenOnePast.getAdjacents().contains(fenetre.getTerritoryChosenOne())) {
                                    // click on a territory different from the previous one and if it is an opponent
                                    Territory theChosenOne = fenetre.getTerritoryChosenOne();
                                    //fenetre.setDashboardPanelRelativeTo(player, "", 0);
                                    if (theChosenOnePast.getArmy_soldiers().size()
                                            + theChosenOnePast.getArmy_cannons().size()
                                            + theChosenOnePast.getArmy_riders().size() != 0) {
                                        if (theChosenOnePast.getArmy_soldiers().size() > 3) {
                                            int[] fighters = {3,0,0};
                                            System.out.println(fighters[0] + " soldats attaquants");
                                            theChosenOnePast.AllMightO(fighters, theChosenOne);
                                        } else {
                                            int[] fighters = {theChosenOnePast.getArmy_soldiers().size()-1, 0, 0};
                                            System.out.println(fighters[0] + " soldats attaquants");
                                            theChosenOnePast.AllMightO(fighters, theChosenOne);
                                        }
                                        break;
                                    }
                                } else if (fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                                        fenetre.getTerritoryChosenOne().getArmy_soldiers().size() > 1) {
                                    /**
                                     * TODO
                                     * or get_Army_riders() > 1 or get_Army_cannons.size() > 1
                                      */
                                    // click on its own territories
                                    // A Changer en fonction du nombre de soldiers, riders et cannons
                                    theChosenOnePast = fenetre.getTerritoryChosenOne();
                                    fenetre.setDashboardPanelRelativeTo(player, theChosenOnePast.getName(), 0);
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
