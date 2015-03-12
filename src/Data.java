import java.io.File;
import java.util.Scanner;

/**
 * Created by aloys on 12/03/15.
 */
public class Data {

    public static final int DISPO = -1;
    public static final int INDISPO = -2;

    class Serveur {
        public Serveur() {
            capacite = 0;
            taille = 0;
            groupe = -1;
            rangee = -1;
            position = -1;
        }

        int capacite;
        int taille;
        int groupe;
        int rangee;
        int position;
    }

    int rangees;
    int emplacements;
    int num_indispo;
    int groupes;
    int num_serveurs;
    Serveur[] serveurs;
    int[][] grille;


    public Data() {
    }

    public boolean read(String filename) {
        File f = new File(filename);
        try {
            Scanner sc = new Scanner(f);
            rangees = sc.nextInt();
            emplacements = sc.nextInt();
            num_indispo = sc.nextInt();
            groupes = sc.nextInt();
            num_serveurs = sc.nextInt();
            grille = new int[rangees][emplacements];
            for (int i = 0; i < rangees; i ++) {
                for (int j = 0; j < emplacements; j ++) {
                    grille[i][j] = DISPO;
                }
            }
            for (int i = 0; i < num_indispo; i ++) {
                int r = sc.nextInt();
                int e = sc.nextInt();
                grille[r][e] = INDISPO;
            }
            serveurs = new Serveur[num_serveurs];
            for (int i = 0; i < num_serveurs; i ++) {
                serveurs[i].taille = sc.nextInt();
                serveurs[i].capacite = sc.nextInt();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
