
/**
 * Created by antoine on 12/03/15.
 */



public class Grid {

    public Serveur serveurs[][];
    public Serveur serveurVide = new Serveur(); //correspond aux emplacements pris par un serveur vide

    public Grid(Data d){
        serveurs = new Serveur[d.rangees][d.emplacements];


        //remplissage en vide ou bien
        for(int i = 0;i<d.rangees;i++)
        {
            for(int j = 0;j<d.emplacements;j++){
                if(d.grille[i][j] == Data.INDISPO)
                    serveurs[i][j] = serveurVide;

            }
        }
    }
}
