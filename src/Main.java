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
        while (!match.getVictory()) {

            for (Territory territory : google.getTerritories()) {
                match.setVictory(territory, territory.getPlayer(),
                        territory.getPlayer().getMission(), google.getRegions().get(0));
                if (match.getVictory()) {
                    winner = territory.getPlayer().getName();
                    break;
                }
            }
        }
        System.out.println(winner + "wins.");
    }
}
