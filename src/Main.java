/**
 * Created by aloys on 12/03/15.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello !");
        Data d = new Data();
        d.test();
        Grid grille = new Grid(d);
        try {
            grille.remplirAleatoirement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
