import java.util.ArrayList;
import java.util.List;

/**
 * Created by antoine on 12/03/15.
 */



public class Grid {

    public Serveur serveurs[][];
    public Serveur serveurIndisponible = new Serveur(); //correspond aux emplacements pris par un serveur vide
    public Data data;
    public ArrayList<Integer> ordre;

    public Grid(Data d){
        data = d;
        serveurs = new Serveur[d.rangees][d.emplacements];


        //remplissage en vide ou en null
        for(int i = 0;i<d.rangees;i++)
        {
            for(int j = 0;j<d.emplacements;j++){
                if(d.grille[i][j] == Data.INDISPO)
                    serveurs[i][j] = serveurIndisponible;
                else
                    serveurs[i][j] = null;

            }

        }
        ordre = new ArrayList<Integer>();
        for(int i = 0;i < d.rangees;i++)
            ordre.add(i);
    }


    public void remplirAleatoirement() throws Exception {
        for(int i = 0;i<data.num_serveurs;i++){
            java.util.Collections.shuffle(ordre);
            int k = 0;
            while(allouer(data.serveurs[i],ordre.get(k)) && k < data.rangees)
                ;

        }

    }


    private boolean allouer(Serveur s, int rangee){ //true si tout s'est bien passé, false sinon
        int k = 0;
        while(!allouer(s, rangee, k) && k<data.emplacements)
            k++;
        if(k==data.emplacements)
            return false;
        else
            return true;
    }

    private boolean allouer(Serveur s, int rangee, int emplacement){ //true si tout s'est bien passé, false sinon
        if(emplacement + s.taille >= data.emplacements)
            return false;
        else
        {
            int k = 0;
            while(serveurs[rangee][emplacement + k] == null &&  k < s.taille && data.grille[rangee][emplacement+k] == -1)
                k++;
            if(k==s.taille){ //si aucun emplacement n'est occupé
                s.rangee = rangee;
                s.position = emplacement;
                for(k=0;k<s.taille;k++) {
                    serveurs[rangee][emplacement + k] = s;
                    data.grille[rangee][emplacement + k] = s.numero;
                }

            }
            else
                System.out.println("Impossible de mettre le serveur ici");
                return false;



        }
    }

    private void desallouer(Serveur s){
        // a faire
    }

}
