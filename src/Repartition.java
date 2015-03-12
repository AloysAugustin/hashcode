import java.util.HashMap;
import java.util.Collections;

/**
 * Created by aloys on 12/03/15.
 *
 * Essaie d'affecter des groupes aux serveurs une fois qu'ils sont places
 *
 */
public class Repartition {
	
    public void try1(Data d, Grid g) {
        HashMap<Integer, Integer[]> state; //cle: groupe (peut etre -1), valeur: puissance de calcul ds chaque ligne
        state = buildState(d, g);

        Integer[] puissancesRestantes = state.get(-1);
        int puissanceRestanteTotale = 0;
        
        for (int i=0; i<puissancesRestantes.length; i++) {
        	puissanceRestanteTotale += puissancesRestantes[i];
        }
        
        while (puissanceRestanteTotale > 0) {
        	int groupe = choisirGroupe(state, d.groupes);
        	giveServer (g, state, groupe);
        }
    }

    /**
     * 
     * @param d : data
     * @param g : grid
     * @return initialized state (0 to all groups except for the no-group)
     */
    static HashMap<Integer, Integer[]> buildState(Data d, Grid g) {
    	HashMap<Integer, Integer[]> state = new HashMap<Integer, Integer[]> ();
        state.put(-1, new Integer [d.rangees +1]);

        for (int i = 0; i < d.groupes; i++)
        	state.put(i,  new Integer[d.rangees +1]);
        
        Integer[] puissances = state.get(-1);
        for (int r = 0; r < d.rangees; r ++) {
            int puissance = 0;
            for (int e = 0; e < d.emplacements;) {
            	if (g != null) {
            		puissance += g.serveurs[r][e].capacite;
            		e += g.serveurs[r][e].taille;
            	}
            	else e++;
            }
            
            puissances[r] = puissance;
            puissances[d.rangees] += puissance;
        }
        
        return state;
    }
    
    
    /**
     * Update state when assign the server at rangee, emplacement to the group
     * Must be valid groups
     */
    static void updateState (Grid g, HashMap<Integer, Integer[]> state, int rangee, int emplacement, int group) {
    	state.get(group)[rangee] += g.serveurs[rangee][emplacement].capacite;
    	state.get(-1)[rangee] -= g.serveurs[rangee][emplacement].capacite;
    	state.get(-1)[state.get(-1).length] += g.serveurs[rangee][emplacement].capacite;
    	g.serveurs[rangee][emplacement].groupe = group;
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
}
