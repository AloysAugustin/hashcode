/**
 * Created by aloys on 12/03/15.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello !");
        Data d = new Data();
        d.test();
        d.print_grille();

        try {
            d.remplirAleatoirement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Blouh");

        d.print_grille();
        System.out.println("Blouh 2");
        Optim.data = d;
    }
}
