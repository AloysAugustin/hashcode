import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


public class Optim {
	public static Data data;
	/*
	public static Data data;
	
	public static void main(String[] args) {
		data = new Data();
		data.read("1.in");
		
	}
	public static boolean tryWith(int result)
	{
		LinkedList<Integer> lst = new LinkedList<Integer>();
		LinkedList<Integer> lsti = new LinkedList<Integer>();
		for (int l = 0; l < data.rangees; ++l)
		{
			lst.clear();
			int current = 0;
			for (int i = 0; i < data.emplacements; ++i)
			{
				if (data.grille[l][i] == Data.INDISPO)
				{
					if (current > 0)
					{
						lst.add(current);
						lsti.add(i - current);
					}
				} else {
					++current;
				}
			}
			LinkedList<Data.Serveur> servers;
			for (int s = 0; s < data.serveurs.length; ++s)
			{
				if (lst.contains(data.serveurs[s].taille))
				{
					int index = lst.indexOf(data.serveurs[s].taille);
					lst.remove(index);
					int ci = lsti.remove(index);
					data.grille[l][ci] = s;
				}
			}
		}
		return true;
	}
	*/
	
	public static int eval()
	{
		int[] totals = new int[data.groupes];
		int[] maxs = new int[data.groupes];
		for (int l = 0; l < data.rangees; ++l)
		{
			int[] current = new int[data.groupes];
			for (int i = 0; i < data.emplacements;)
			{
				if (data.grille[l][i] >= 0)
				{
					int ms = data.grille[l][i];
					current[data.serveurs[ms].groupe] += data.serveurs[ms].capacite;
					i += data.serveurs[ms].taille;
				} else {
					++i;
				}
			}
			for (int k = 0; k < data.groupes; ++k)
			{
				totals[k] += current[k];
				if (current[k] > maxs[k])
					maxs[k] = current[k];
			}
		}
		int worse = Integer.MAX_VALUE;
		for (int k = 0; k < data.groupes; ++k)
		{
			int current = totals[k] - maxs[k];
			if (current < worse)
				worse = current;
		}
		return worse;
	}
	
	public static void optim()
	{
		/* 1: Optimize server choice */
		LinkedList<Integer> useless = new LinkedList<Integer>();
		for (int j = 0; j < data.serveurs.length; ++j)
		{
			if (data.serveurs[j].position < 0)
				useless.add(j);
		}
		Comparator<Integer> myComp = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return data.serveurs[o2].capacite - data.serveurs[o1].capacite;
			}
		};
		Collections.sort(useless, myComp);
		LinkedList<Integer> servers = new LinkedList<Integer>();
		int sstart = 0;
		for (int l = 0; l < data.rangees; ++l)
		{
			for (int i = 0; i < data.emplacements;)
			{
				if (data.grille[l][i] >= 0)
				{
					int ms = data.grille[l][i];
					for (Integer j : useless)
					{
						if (myComp.compare(j, ms) >= 0)
							break;
						if (data.serveurs[j].taille <= data.serveurs[ms].taille)
						{
							data.serveurs[j].groupe = data.serveurs[ms].groupe;
							data.serveurs[j].position = i;
							data.serveurs[j].rangee = l;
							data.serveurs[ms].position = -1;
							data.grille[l][i] = j;
							// Add DISPO
							int empty = data.serveurs[ms].taille - data.serveurs[j].taille;
							if (empty > 0)
							{
								int offset = i + data.serveurs[ms].taille;
								while (empty > 0)
								{
									--offset;
									data.grille[l][offset] = Data.DISPO;
									--empty;
								}
							}
							// fin DISPO
							useless.removeFirstOccurrence(j);
							useless.add(ms);
							Collections.sort(useless, myComp);
							ms = j;
							break;
						}
					}
					servers.add(ms);
					i += data.serveurs[ms].taille;
				} else if (data.grille[l][i] == Data.INDISPO)
				{
					servers.clear();
					++i;
					sstart = i;
				} else {
					int free = 1;
					while (data.grille[l][i + free] == Data.DISPO)
						++free;
					int pos = sstart;
					repLoop: while (free > 0)
					{
						for (Integer ms : servers)
						{
							for (Integer j : useless)
							{
								if (myComp.compare(j, ms) >= 0)
									break;
								if (data.serveurs[j].taille <= data.serveurs[ms].taille + free)
								{
									data.serveurs[j].groupe = data.serveurs[ms].groupe;
									data.serveurs[j].position = pos;
									data.serveurs[j].rangee = l;
									data.serveurs[ms].position = -1;
									data.grille[l][pos] = j;
									useless.removeFirstOccurrence(j);
									useless.add(ms);
									Collections.sort(useless, myComp);
									int decal = data.serveurs[j].taille - data.serveurs[ms].taille;
									free -= decal;
									for (int iter = i + decal - 1; iter >= pos + decal; --iter)
										data.grille[l][iter] = data.grille[l][iter - decal];
									i += decal;
									continue repLoop;
								}
							}
							pos += data.serveurs[ms].taille;
						}
						break;
					}
					i += free;
				}
			}
			servers.clear();
		}
		
		/* 2: Optimize group choice */
		// TODO
	}
}
