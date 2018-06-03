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
                fenetre.setDashboardPanelRelativeTo(player, "", toPlace);
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
        /*while (!match.getVictory()) {

            for (Territory territory : google.getTerritories()) {
                match.setVictory(territory, territory.getPlayer(),
                        territory.getPlayer().getMission(), territory.getRegion());
                if (match.getVictory()) {
                    winner = territory.getPlayer().getName();
                    break;
                }
            }
        }*/
        System.out.println(winner + " wins.");
    }
}
