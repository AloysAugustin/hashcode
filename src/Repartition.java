import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Collections;

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

        Integer[] puissancesRestantes = state.get(-1);
        
        while (puissancesRestantes[puissancesRestantes.length-1] > 0) {
        	int groupe = choisirGroupe(state, d.groupes);
        	giveServer (d, state, groupe);
        }
    }

    /**
     * 
     * @param d : data
     * @return initialized state (0 to all groups except for the no-group)
     */
    static HashMap<Integer, Integer[]> buildState(Data d) {
    	HashMap<Integer, Integer[]> state = new HashMap<Integer, Integer[]> ();
        Integer[] puissance = new Integer [d.rangees +1];
        for (int i=0; i < d.rangees + 1; i++) {
            puissance[i] = new Integer(0);
        }
        state.put(-1, puissance);

        for (int i = 0; i < d.groupes; i++) {
             puissance = new Integer [d.rangees +1];
            for (int j=0; j < d.rangees + 1; j++) {
                puissance[j] = new Integer(0);
            }
            state.put(i, puissance);
        }
        
        Integer[] puissances = state.get(-1);
        System.out.println(state.get(-1)[3]);
        for (int r = 0; r < d.rangees; r ++) {
            int p = 0;
            for (int e = 0; e < d.emplacements;) {
            	if (d.grille[r][e] >= 0) {
            		p += d.serveurs[d.grille[r][e]].capacite;
            		e += d.serveurs[d.grille[r][e]].taille;
            	}
            	else e++;
            }
            
            puissances[r] = p;
            System.out.println(puissances[d.rangees]);
            puissances[d.rangees] += p;
        }
        
        return state;
    }
    
    
    /**
     * Update state when assign the server at rangee, emplacement to the group
     * Must be valid groups
     */
    static void updateState (Data d, HashMap<Integer, Integer[]> state, int rangee, int emplacement, int group) {
    	state.get(group)[rangee] += d.serveurs[d.grille[rangee][emplacement]].capacite;
        state.get(group)[state.get(-1).length - 1] += d.serveurs[d.grille[rangee][emplacement]].capacite;
        state.get(-1)[rangee] -= d.serveurs[d.grille[rangee][emplacement]].capacite;
    	state.get(-1)[state.get(-1).length - 1] -= d.serveurs[d.grille[rangee][emplacement]].capacite;
        d.serveurs[d.grille[rangee][emplacement]].groupe = group;
    }
    
    /**
     * Returns the group with the smallest minimal capacity
     * @param state
     * @param nbGroupes
     * @return
     */
    static int choisirGroupe(HashMap<Integer, Integer[]> state, int nbGroupes) {
    	int groupe = -1;
    	int minimum = Integer.MAX_VALUE;
    	for (int i=0; i<nbGroupes; i++) {
    		Integer[] puissances = state.get(i);
    		
    		int max = 0;
    		for (int j=0; j<puissances.length -1; j++) {
    			if (max < puissances[j])
    				max = puissances[j];
    		}
    		
    		if (minimum > puissances[puissances.length-1] - max) {
    			minimum = puissances[puissances.length-1] - max;
    			groupe = i;
    		}
    	}
    	
    	return groupe;
    }
    
    /**
     * Tries to add a server on the smallest rows
     * @param d
     * @param state
     * @param groupe
     */
    static void giveServer(Data d, HashMap<Integer, Integer[]> state, int groupe) {
    	Integer[] puissances = state.get(groupe);
    	Integer[] puissanceLibre = state.get(-1);
    	Integer[] rangees = argsort(puissances);
    	Serveur serveur;
    	int r = 0;
    	int indexe =0;
    	for (r=0; r<rangees.length; r++) {
    		if (puissanceLibre[r] > 0) {
    			serveur = d.serveurs[d.grille[r][0]];
    			indexe = 0;
    			while (serveur == null || serveur.groupe != -1) {
    				if (serveur == null)
    					indexe ++;
    				else
    					indexe += serveur.taille;
    			}   			
    			break;
    		}
    	}
    	
    	updateState(d, state, r, indexe, groupe);
    }
    
 
    public static Integer[] argsort(final Integer[] a) {
        Integer[] indexes = new Integer[a.length];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = i;
        }
        Arrays.sort(indexes, new Comparator<Integer>() {
            public int compare(Integer i1, Integer i2) {
                return Float.compare(a[i1], a[i2]);
            }
        });
        return indexes;
    }

}
