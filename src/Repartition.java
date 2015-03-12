import java.util.HashMap;

/**
 * Created by aloys on 12/03/15.
 *
 * Essaie d'affecter des groupes aux serveurs une fois qu'ils sont places
 *
 */
public class Repartition {

    public static void try1(Data d) {
        HashMap<Integer, Integer[]> state; //cle: groupe (peut etre -1), valeur: puissance de calcul ds chaque ligne
        state = buildState(d);
    }

    static HashMap<Integer, Integer[]> buildState(Data d) {
        for (int r = 0; r < d.rangees; r ++) {
            int serveur = 0;
            int lastserveur = -2;
            for (int e = 0; e < d.emplacements; e ++) {

            }
        }
    }
}
