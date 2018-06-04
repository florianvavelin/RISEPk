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
        //fenetre.setDashboardPanelRelativeTo((new Player("Iskandar",Color.white,true)), "");
        Match match = new Match(false);
        String winner = "";
        int army = 50 - 5*google.getAllPlayers().size();
        for (Player player : google.getAllPlayers()) {
            int toPlace = army - player.getTerritories().size();
            while (toPlace > 0) {
                fenetre.setDashboardPanelRelativeTo(player, " ", toPlace);
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
                boolean goOnMan = false;
                for (Territory territory : player.getTerritories()) {
                    if (territory.getArmy_soldiers().size() == 1) {
                        goOnMan = false;
                        break;
                    } else {
                        goOnMan = true;
                    }
                }
                int OmaiWaMoShindeiru = 3;
                while (OmaiWaMoShindeiru > 0 && goOnMan) {
                    boolean notYouTerritory = true;
                    fenetre.setWaitForClick(true);
                    while (fenetre.isWaitForClick() && notYouTerritory) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (fenetre.getTerritoryChosenOne() != null) {
                            if (fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                                    fenetre.getTerritoryChosenOne().getArmy_soldiers().size() > 1) {
                                fenetre.setWaitForClick(false);
                                notYouTerritory = false;
                            } else {
                                notYouTerritory = true;
                            }
                        }
                    }
                    Territory theChosenOne = fenetre.getTerritoryChosenOne();
                    System.out.print(player.getName() + ": " + theChosenOne.getName() + " against ");
                    fenetre.setWaitForClick(true);
                    notYouTerritory = true;
                    while (fenetre.isWaitForClick() && notYouTerritory) {
                        try {
                            Thread.sleep(10);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (fenetre.getTerritoryChosenOne() != null) {
                            if (!fenetre.getTerritoryChosenOne().getPlayer().equals(player) &&
                                    theChosenOne.getAdjacents().contains(fenetre.getTerritoryChosenOne())) {
                                fenetre.setWaitForClick(false);
                                notYouTerritory = false;
                            } else {
                                notYouTerritory = true;
                            }
                        }
                    }
                    Territory theChosenTwo = fenetre.getTerritoryChosenOne();
                    System.out.println(theChosenTwo.getName());
                    if (theChosenOne.getArmy_soldiers().size() > 3) {
                        int[] fighters = {3,0,0};
                        theChosenOne.AllMightO(fighters, theChosenTwo);
                    } else {
                        int[] fighters = {theChosenOne.getArmy_soldiers().size()-1, 0, 0};
                        theChosenOne.AllMightO(fighters, theChosenTwo);
                    }
                    fenetre.setUnitsOnMap();
                    OmaiWaMoShindeiru--;
                }
                if (player.getTerritories().size() == google.getTerritories().size()) {
                    match.setVictory(true);
                    winner = player.getName();
                    break;
                }
            }
            /*for (Territory territory : google.getTerritories()) {
                match.setVictory(territory, territory.getPlayer(),
                        territory.getPlayer().getMission(), territory.getRegion());
                if (match.getVictory()) {
                    winner = territory.getPlayer().getName();
                    break;
                }
            }*/
        }
        System.out.println(winner + " wins.");
    }
}
