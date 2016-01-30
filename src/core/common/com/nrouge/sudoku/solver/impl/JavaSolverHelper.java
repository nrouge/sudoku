package com.nrouge.sudoku.solver.impl;

import java.util.Arrays;

import com.nrouge.sudoku.model.Case;
import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.ICaseChangeListener;
import com.nrouge.sudoku.solver.ISolver;
import com.nrouge.sudoku.solver.MultipleSolutionException;
import com.nrouge.sudoku.solver.UndeterminedSolutionException;
import com.nrouge.sudoku.solver.UnsolvableCaseException;
import com.nrouge.sudoku.util.PossibilitesUtils;

class JavaSolverHelper {
	
	//private static final Log log = LogFactory.getLog(NicoSolverHelper.class);

	final Grille g;
	final ICaseChangeListener ccl;
	final int length;
	final int length2;
	final int length3;
	final byte puissance;
	final Case[][] cs;
	
	JavaSolverHelper(Grille g, ICaseChangeListener ccl) {
		this.g = g;
		this.ccl = ccl;
		length = g.getLength();
		length2 = length << 1;
		length3 = 3 * length;
		puissance = g.getPuissance();
		cs = new Case[length3][length];
		for (int i = 0; i < length ; i++) {
			for (int j = 0; j < length; j++) {
				cs[i][j] = g.getCase(i, j); //lignes
				cs[length + i][j] = g.getCase(j, i); //colonnes
				cs[length2 + i][j] = g.getCase(puissance * (i / puissance) + (j / puissance), puissance * (i % puissance) + j % puissance); //carrés
			}
		}
	}

	/**
	 * Résolution niveau 0 : ensemble des valeurs possibles d'une case = tout - ensemble des valeurs des autres cases.
	 * Les autres cases sont prises dans la ligne, la colonne et le carré auxquels appartient la case en cours.
	 * L'algo s'arrête à la première case résolue
	 * @return true si une case a été résolue
	 * @throws UnsolvableCaseException
	 */
	final boolean solve0()throws UnsolvableCaseException {
		for (int i = 0; i < length; i++) {
			Case[] lig = cs[i]; //ligne de la case (i,j)
			for (int j = 0; j < length; j++) {
				Case c = lig[j];
				if (!c.isSolved()) {
					Case[] col = cs[length + j]; //colonne de la case (i,j)
					Case[] car = cs[length2 + puissance * (i / puissance) + (j / puissance)]; //carré de la case (i,j)
					long pAvant = c.getPossibilites();
					long pos = pAvant;
					pos &= calculePossibilites(lig);
					pos &= calculePossibilites(col);
					pos &= calculePossibilites(car);
					if (pos == pAvant) continue;
					updatePossiblites(c, pos, 0);
					if (c.isSolved()) return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Renvoie le masque des valeurs encore possibles d'un ensemble de cases.
	 * @param cases
	 * @return
	 */
	private long calculePossibilites(Case[] es) {
		final int length = es.length;
		long res = (1 << length) - 1;
		for (int i = 0; i < length; i++) {
			Case c = es[i];
			if (c.isSolved()) {
				res &= ~(1 << c.getValeur());
			}
		}
		return res;
	}

	
	/**
	 * Recherche dans les ensembles d'une valeur possible qui se répète une seule fois 
	 * @return true si une case a été résolue
	 */
	final boolean solve1() {
		final int[] posCount = new int[length]; // compteur de répétition sur les valeurs
		final int[] idx = new int[length]; // dernier index de la case dans l'ensemble qui a la valeur en possibilité
		for (int i = 0; i < length3; i++) { // parcourt des ensembles
			Case[] es = cs[i]; // ensemble en cours (ligne, colonne ou carré)
			for (int j = 0; j < length; j++) { // boucle sur les cases de l'ensemble
				Case c = es[j];
				if (!c.isSolved()) {
					final long pos = c.getPossibilites();
					for (int v = 0 ; v < length; v++) { // boucle sur les valeur possibles
						long vMask = 1 << v;
						if ((vMask & pos) == vMask) {
							posCount[v]++;
							idx[v] = j;
						}
					}
				}
			}
			for (int v = 0; v < length; v++) {
				if (posCount[v] == 1) { // test si la valeur est répétée une seule fois 
					Case c = cs[i][idx[v]];
					c.setValeur(v);
					if (ccl != null) ccl.caseHasChanged(c);
					//if (log.isInfoEnabled()) log.info("1:"+c.getId()+"="+g.getCharValeurs().toChar(v));
					return true;
				}
			}
			Arrays.fill(posCount, 0); //réinitialisation de posCount (pas la peine de réinitialiser idx)
		}
		return false;
	}
	
	/**
	 * Recherche dans les ensembles de n répétitions de n possiblités (ou d'un sous-ensemble de ces n possibilités)
	 * @return 0 si des possibilités ont été otées, -1 si rien n'a été fait, et sinon le nombre de cases résolues
	 * @throws UnsolvableCaseException
	 */
	final int solve2() throws UnsolvableCaseException {
		final int[] idx = new int[length]; //index dans l'ensemble de chaque case non résolue
		final long[] pos = new long[length]; //possiblités de chaque case non résolue
		final int[] nbPos = new int[length]; //nombre de possibilités pour chaque case non résolue
		for (int i = 0; i < length3; i++) {
			final Case[] es = cs[i]; // ensemble de cases (ligne, colonne ou carré)
			int nbNotSolved = 0; // nombre de cases non résolues de l'ensemble
			long vMask = 0; // masque des possibilités restantes pour l'ensemble
			for (int j = 0; j < length; j++) {
				Case c = es[j];
				if (!c.isSolved()) {
					long p = c.getPossibilites();
					idx[nbNotSolved] = j;
					pos[nbNotSolved] = p;
					nbPos[nbNotSolved] = PossibilitesUtils.getNbPossibilites(p);
					vMask |= p;
					nbNotSolved++;
				}
			}
			if ((vMask != 0) && (nbNotSolved > 3)) {
				//il y a au moins 3 cases non résolues
				// => on fait varier n de 2 à min(<nombre de cases non résolues>,<nombre de valeurs possibles>) - 1
				/*if (log.isDebugEnabled()) {
					StringBuffer sb = new StringBuffer();
					for (int j = 0; j < nbNotSolved; j++) sb.append((j == 0) ? "" : "|").append(toString(PossibilitesUtils.getValeursPossibles(pos[j])));
					log.debug("2:i="+i+":"+sb.toString());
				}*/
				final int nb = PossibilitesUtils.getNbPossibilites(vMask);
				final int N = Math.min(nbNotSolved, nb) - 1;
				//if (log.isDebugEnabled()) log.debug("2:i="+i+",nb="+nb+",nbNotSolved="+nbNotSolved+",N="+N);
				for (int n = 2; n <= N; n++) { // boucle sur le nombre de répétitions
					//il faut qu'il y ait au moins n cases ayant au plus n possiblités
					int nbCaseOK = 0;
					for (int j = 0; j < nbNotSolved; j++) if (nbPos[j] <= n) nbCaseOK++;
					if (nbCaseOK < n) continue;
					//if (log.isDebugEnabled()) log.debug("2:i="+i+",n="+n+",pos="+toBinaryString(vMask));
					//on construit un deuxième tableau d'index pour les cases à tester
					final int[] idx2 = new int[nbCaseOK];
					int c = 0;
					for (int j = 0; j < nbNotSolved; j++) if (nbPos[j] <= n) idx2[c++] = j;
					//on essaie toutes les combinaisons des n valeurs
					final long[] p = PossibilitesUtils.getPossibilitesMasques(vMask, n);
					final int pLength = p.length;
					for (int j = 0; j < pLength; j++) {
						final int[] compteur = new int[n];
						for (int k = 0; k < n; k++) compteur[k] = k;
						do {
							long p2 = 0;
							for (int l = 0; l < n; l++) p2 |= pos[idx2[compteur[l]]];
							if (p2 != p[j]) continue;
							c = 0;
							int nbCasesResolues = 0;
							int nbCasesModifiees = 0;
							for (int l = 0; l < nbNotSolved; l++) {
								if (l == idx2[compteur[c]]) {
									if (c < n - 1) c++;
									continue;
								}
								Case cas = es[idx[l]];
								long pAvant = cas.getPossibilites();
								long pApres = ~p2 & pAvant;
								if (pAvant == pApres) continue;
								updatePossiblites(cas, pApres, 2);
								nbCasesModifiees++;
								if (cas.isSolved()) nbCasesResolues++;
							}
							if (nbCasesResolues > 0) return nbCasesResolues;
							if (nbCasesModifiees > 0) return 0;
						} while (PossibilitesUtils.incrementeCompteur(compteur, nbCaseOK));
					}
				}
			}
		}
		return -1;
	}
	
	/**
	 * Algo : si une possibilité se trouve uniquement sur une ligne ou une colonne d'un carré, elle ne peut pas se
	 * trouver dans les autres lignes ou colonnes des autres carrés. 
	 * @return
	 * @throws UnsolvableCaseException
	 */
	final int solve3() throws UnsolvableCaseException{
		final long[] posLigne = new long[puissance]; //possibilités pour la ligne du carré
		final long[] posColonne = new long[puissance]; //possibilités pour la colonne du carré
		final long[] posAutreLigne = new long[puissance]; //possibilités pour les autres lignes du carré
		final long[] posAutreColonne = new long[puissance]; //possibilités pour les autres colonnes du carré
		//parcourt des carrés
		for (int a = 0; a < puissance; a++)
		for (int b = 0; b < puissance; b++) {
			final Case[] carre = cs[length2 + puissance * a + b];
			long vMask = 0;
			for (int i = 0; i < length; i++) {
				Case c = carre[i];
				if (!c.isSolved()) vMask |= c.getPossibilites();
			}
			if (vMask == 0) continue;
			//le carré a au moins une case non résolue.
			for (int i = 0; i < puissance; i++)
			for (int j = 0; j < puissance; j++) {
				final int idx = puissance * i + j;
				final Case c = carre[idx];
				if (c.isSolved()) continue;
				final long p = c.getPossibilites();
				posLigne[i] |= p;
				posColonne[j] |= p;
				for (int k = 0; k < puissance; k++) {
					if (k != i) posAutreLigne[k] |= p;
					if (k != j) posAutreColonne[k] |= p;
				}
			}
			final int[] valeurs = PossibilitesUtils.getValeursPossibles(vMask);
			final long valeursLength = valeurs.length;
			for (int i = 0; i < valeursLength; i++) {
				final int v = valeurs[i];
				final long p = 1 << v;
				//recherche des lignes ou colonnes contenant cette valeur, sachant que les autres
				//lignes ou colonnes ne doivent pas la contenir
				for (int k = 0; k < puissance; k++) {
					final long pal = posAutreLigne[k];
					if (((p & posLigne[k]) == p) && (pal != 0) && ((p & pal) == 0)) {
						Case[] lig = cs[puissance * a + k];
						int nbCasesResolues = 0;
						int nbCasesModifiees = 0;
						for (int j = 0; j < length; j++) {
							if (j / puissance == b) continue;
							Case cas = lig[j];
							if (cas.isSolved()) continue;
							long pAvant = cas.getPossibilites();
							long pApres = ~p & pAvant;
							if (pAvant == pApres) continue;
							updatePossiblites(cas, pApres, 3);
							nbCasesModifiees++;
							if (cas.isSolved()) nbCasesResolues++;
						}
						if (nbCasesResolues > 0) return nbCasesResolues;
						if (nbCasesModifiees > 0) return 0;
					}
					final long pac = posAutreColonne[k];
					if (((p & posColonne[k]) == p) && (pac != 0) && ((p & pac) == 0)) {
						Case[] col = cs[length + puissance * b + k];
						int nbCasesResolues = 0;
						int nbCasesModifiees = 0;
						for (int j = 0; j < length; j++) {
							if (j / puissance == a) continue;
							Case cas = col[j];
							if (cas.isSolved()) continue;
							long pAvant = cas.getPossibilites();
							long pApres = ~p & pAvant;
							if (pAvant == pApres) continue;
							updatePossiblites(cas, pApres, 3);
							nbCasesModifiees++;
							if (cas.isSolved()) nbCasesResolues++;
						}
						if (nbCasesResolues > 0) return nbCasesResolues;
						if (nbCasesModifiees > 0) return 0;
					}
				}
			}
			Arrays.fill(posLigne, 0);
			Arrays.fill(posColonne, 0);
			Arrays.fill(posAutreLigne, 0);
			Arrays.fill(posAutreColonne, 0);
		}
		return -1;
	}
	
	/**
	 * Tentative de résolution en testant toutes les valeurs possibles de la première case ayant le moins de possibilités.
	 * @return
	 */
	boolean solve4(int level, ISolver solver) throws MultipleSolutionException, UndeterminedSolutionException {
		Case c = null;
		int nbPos = length;
		int x = -1;
		int y = -1;
		for (int i = 0; i < length; i++)
		for (int j = 0; j < length; j++) {
			final Case c2 = g.getCase(i, j);
			if (c2.isSolved()) continue;
			final int nbPos2 = PossibilitesUtils.getNbPossibilites(c2.getPossibilites());
			if (nbPos2 < nbPos) {
				c = c2;
				nbPos = nbPos2;
				x = i;
				y = j;
			}
		}
		if (c == null) {
			//NORMALEMENT IMPOSSIBLE
			throw new IllegalStateException();
		}
		final int[] valeursPossibles = PossibilitesUtils.getValeursPossibles(c.getPossibilites());
		final int nbValeursPossibles = valeursPossibles.length;
		//on teste toutes les valeurs possibles
		int valeur = -1; //valeur qui convient
		for (int i = 0; i < nbValeursPossibles; i++) {
			final Grille g2 = (Grille) g.clone();
			final Case c2 = g2.getCase(x, y);
			c2.setValeur(valeursPossibles[i]);
			boolean test;
			try {
				test = solver.solve(g2, level - 1);
			} catch (UnsolvableCaseException uce) {
				continue;
			} catch (MultipleSolutionException mse) {
				throw mse;
			} catch (UndeterminedSolutionException use) {
				throw use;
			}
			if (!test) {
				//la solution est indéterminée si on a pas résolu au niveau 4 (il faudrait encore faire des suppositions pour être sûr)
				if (level == 4) throw new UndeterminedSolutionException("Solution indéterminée");
				//sinon, on continue de regarder les autres possibilités
				continue;
			}
			if (valeur != -1) { //on a déjà une solution => la solution est donc multiple
				throw new MultipleSolutionException();
			}
			valeur = valeursPossibles[i];
		}
		if (valeur == -1) {
			//aucune valeur ne convient
			return false;
		}
		c.setValeur(valeur);
		if (ccl != null) ccl.caseHasChanged(c);
		//if (log.isInfoEnabled()) log.info("4:"+c.getId()+"="+g.getCharValeurs().toChar(valeur));
		return true;
	}
	
	/*private String toBinaryString(long pos) {
		return PossibilitesUtils.toBinaryString(pos, length);
	}*/
	
	/*private String toString(int[] va) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < va.length; i++) {
			sb.append(g.getCharValeurs().toChar(va[i]));
		}
		return sb.toString();
	}*/
	
	private void updatePossiblites(Case c, long newPos, int level) throws UnsolvableCaseException {
		//if (log.isInfoEnabled()) log.info(level+":"+c.getId()+":"+toBinaryString(c.getPossibilites())+"->"+toBinaryString(newPos));
		c.setPossibilites(newPos);
		if (ccl != null) ccl.caseHasChanged(c);
		if (newPos == 0) {
			throw new UnsolvableCaseException(c);
		}
		if (c.calculerIsSolved()) {
			if (ccl != null) ccl.caseHasChanged(c);
		}
		//if (c.calculerIsSolved() && log.isInfoEnabled()) log.info(level+":"+c.getId()+"="+g.getCharValeurs().toChar(c.getValeur()));
	}

}
