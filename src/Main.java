/**
 * Created by aloys on 12/03/15.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello !");
        Data d = new Data();
        d.test();
        d.print_grille();
        Grid grille = new Grid(d);
        try {
            grille.remplirAleatoirement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Blouh");
        grille.data.print_grille();
    }
}
