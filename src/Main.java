public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();

        while(menu.getFen()==null)
        {
            try{
            Thread.sleep(10);}catch(Exception e){}
        }
        Fenetre fenetre = menu.getFen();
        System.out.println(fenetre.getAllPlayers().size());
    }
}
