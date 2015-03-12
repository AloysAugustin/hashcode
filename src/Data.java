import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by aloys on 12/03/15.
 */
public class Data {

    public static final int DISPO = -1;
    public static final int INDISPO = -2;



    int rangees;
    int emplacements;
    int num_indispo;
    int groupes;
    int num_serveurs;
    Serveur[] serveurs;
    int[][] grille;
    //Serveur serveurs[][];
    Serveur serveurIndisponible = new Serveur(); //correspond aux emplacements pris par un serveur vide


    ArrayList<Integer> ordre;


    public Data() {

    }




    public void remplirAleatoirement() throws Exception {
        System.out.println("Remplir Aléatoirement :");
        List<Serveur> servers = Arrays.asList(serveurs);
        Random rnd = new Random();
        while (!servers.isEmpty())
        {
            java.util.Collections.shuffle(ordre);
            int k = 0;
            Serveur s = servers.remove(rnd.nextInt(servers.size()));
            while(k < rangees && !allouer(s, ordre.get(k)))
                k++;
        }

    }

    private boolean allouer(Serveur s, int rangee){ //true si tout s'est bien passé, false sinon
        int k = 0;
        while(k<emplacements && !allouer(s, rangee, k))
            k++;
        if(k==emplacements)
            return false;
        else
            return true;
    }

    private boolean allouer(Serveur s, int rangee, int emplacement){ //true si tout s'est bien passé, false sinon
        if(emplacement + s.taille > emplacements)
            return false;
        else
        {
            int k = 0;
            while((k < s.taille) && (grille[rangee][emplacement + k] == Data.DISPO))
                k++;
            if(k==s.taille){ //si aucun emplacement n'est occupé
                s.rangee = rangee;
                s.position = emplacement;
                for(k=0;k<s.taille;k++) {
                    grille[rangee][emplacement + k] = s.numero;
                    System.out.println(s.numero);
                }
                return true;

            }
            else
                //System.out.println("Impossible de mettre le serveur ici");
                return false;



        }
    }



    public boolean read(String filename) {
        File f = new File(filename);
        try {
            Scanner sc = new Scanner(f);
            rangees = sc.nextInt();
            ordre = new ArrayList<Integer>(rangees);
            for(int i = 0;i < rangees;i++)
                ordre.add(i,i);
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
                serveurs[i] = new Serveur();
                serveurs[i].taille = sc.nextInt();
                serveurs[i].capacite = sc.nextInt();
                serveurs[i].numero = i;
                serveurs[i].densite = (float) serveurs[i].capacite / (float) serveurs[i].taille;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void print_grille() {
        for (int r = 0; r < rangees; r ++) {
            for (int e = 0; e < emplacements; e ++) {
                System.out.print("" + grille[r][e] + "\t");
            }
            System.out.println();
        }
    }

    public void test() {
        read("dc.in");
        System.out.println("" + rangees + " rangees " + emplacements + " emplacements " + num_indispo + " empl. indispo " +
                        groupes + " groupes " + num_serveurs + " serveurs.");
        /*print_grille();
        for (int i = 0; i < num_serveurs; i ++) {
            System.out.println("C " + serveurs[i].capacite + " T " + serveurs[i].taille);
        }*/
    }

}
