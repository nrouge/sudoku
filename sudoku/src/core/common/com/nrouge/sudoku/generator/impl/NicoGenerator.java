package com.nrouge.sudoku.generator.impl;

import java.util.ArrayList;
import java.util.List;

import com.nrouge.sudoku.generator.IGenerator;
import com.nrouge.sudoku.model.Case;
import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.ISolver;
import com.nrouge.sudoku.solver.MultipleSolutionException;
import com.nrouge.sudoku.solver.SolverException;
import com.nrouge.sudoku.solver.UndeterminedSolutionException;
import com.nrouge.sudoku.solver.UnsolvableCaseException;
import com.nrouge.sudoku.util.PossibilitesUtils;

/**
 * 
 * @author Nicolas Rougé
 */
public final class NicoGenerator implements IGenerator {
	
	//private static final Log log = LogFactory.getLog(NicoGenerator.class);

	/**
	 * @see com.nrouge.sudoku.generator.IGenerator#generate(byte, int)
	 */
	public Grille generate(byte puissance, int level, ISolver solver) {
		Grille res = null;
		do {
			res = new NicoInternalGenerator(puissance, solver, level).generate();
		} while (res == null);
		return res;
	}
	
	private static class NicoInternalGenerator {
		private final Grille grille; //grille résultat
		private final int length;
		private final byte puissance;
		private final ISolver solver;
		private final int level;
		NicoInternalGenerator(byte puissance, ISolver solver, int level) {
			this.puissance = puissance;
			this.solver = solver;
			this.level = level;
			length = puissance * puissance;
			grille = new Grille(puissance);
		}
		Grille generate() {
			boolean finished = false;
			List casesPossibles = getCasesPossibles();
			int nbCaseValorisees = 0;
			do {
				//choix d'une case au hasard
				final int idxCase = (int) (Math.random() * casesPossibles.size());
				final int[] coord = (int[]) casesPossibles.get(idxCase);
				final int i = coord[0];
				final int j = coord[1];
				//récup des possibilités
				final long pos = grille.getCase(i, j).getPossibilites();
				List valeursPossibles = getValeursPossibles(pos);
				boolean valeurTrouvee = false;
				do {
					final int idxValeur = (int) (Math.random() * valeursPossibles.size());
					final int v = ((Integer) valeursPossibles.get(idxValeur)).intValue();
					final int test = (nbCaseValorisees < (length * 2 - 3)) ? 0 : testerAffecterValeur(i, j, v);
					switch (test) {
						case -1:
							valeursPossibles.remove(idxValeur);
							break;
						case 1:
							finished = true;
						default:
							affecterValeur(i, j, v);
							nbCaseValorisees++;
							//if (log.isInfoEnabled()) log.info(Integer.toString(nbCaseValorisees));
							casesPossibles = getCasesPossibles();
							valeurTrouvee = true;
					}
				} while (!valeurTrouvee && valeursPossibles.size() > 0);
				if (!valeurTrouvee) {
					casesPossibles.remove(idxCase);
					if (casesPossibles.size() == 0) {
						//if (log.isInfoEnabled()) log.info("reset pendant création grille");
						return null;
					}
				}
			} while (!finished);
			if (level == 0) return grille;
			//on enlève les case jusqu'à avoir une grille qui n'est pas solvable au niveau (level - 1)
			try {
				final Grille testedGrille = clonerGrille();
				if (!solver.solve(testedGrille, level - 1)) return grille;
			} catch (SolverException se) {
				//NORMALEMENT IMPOSSIBLE
				throw new IllegalStateException();
			}
			List casesValorisees = getCasesValorisees();
			do {
				//choix d'une cases valorisée à supprimer
				final int idxCase = (int) (Math.random() * casesValorisees.size());
				final int[] coord = (int[]) casesValorisees.get(idxCase);
				final int i = coord[0];
				final int j = coord[1];
				//test de suppression
				final int test = testerSupprimerValeur(i, j);
				finished = false;
				switch (test) {
					case -1:
						casesValorisees.remove(idxCase);
						if (casesValorisees.size() == 0) {
							//if (log.isInfoEnabled()) log.info("reset pendant suppression cases valorisées");
							return null;
						}
						break;
					case 0:
						finished = true;
					case 1:
						supprimerValeur(i, j);
						nbCaseValorisees--;
						//if (log.isInfoEnabled()) log.info(Integer.toString(nbCaseValorisees));
						casesValorisees = getCasesValorisees();
				}
			} while (!finished);
			return grille;
		}
		
		/**
		 * @return -1 non, 0 oui (la valeur convient), 1 oui et fin (la valeur convient, et le sudoku est resolvable)
		 */
		private int testerAffecterValeur(int i, int j, int v) {
			Grille testedGrille = clonerGrille();
			testedGrille.getCase(i, j).setValeur(v);
			try {
				return (solver.solve(testedGrille, level)) ? 1 : 0;
			} catch (MultipleSolutionException mse) {
				return -1;
			} catch (UnsolvableCaseException uce) {
				return -1;
			} catch (UndeterminedSolutionException e) {
				return 0;
			}
		}
		
		private void affecterValeur(int i, int j, int v) {
			grille.getCase(i, j).setValeur(v);
			//reset des possibilités
			for (int a = 0; a < length; a++)
			for (int b = 0; b < length; b++) {
				final Case c = grille.getCase(a, b);
				if (c.isSolved()) continue;
				c.setPossibilites((1 << length) - 1);
			}
			//mise à jour des possibilités pour les lignes et colonnes
			for (int a = 0; a < length; a++) {
				long pLigne = 0;
				long pColonne = 0;
				for (int b = 0; b < length; b++) {
					final Case cLigne = grille.getCase(a, b);
					final Case cColonne = grille.getCase(b, a);
					if (cLigne.isSolved()) pLigne |= cLigne.getPossibilites();
					if (cColonne.isSolved()) pColonne |= cColonne.getPossibilites();
				}
				for (int b = 0; b < length; b++) {
					final Case cLigne = grille.getCase(a, b);
					final Case cColonne = grille.getCase(b, a);
					if (!cLigne.isSolved()) cLigne.setPossibilites(~pLigne & cLigne.getPossibilites());
					if (!cColonne.isSolved()) cColonne.setPossibilites(~pColonne & cColonne.getPossibilites());
				}
			}
			//mise à jour des possibilités pour les carrés
			for (int a = 0; a < puissance; a++)
			for (int b = 0; b < puissance; b++) {
				long p = 0;
				for (int k = 0; k < length; k++) {
					final int x = a * puissance + k / puissance;
					final int y = b * puissance + k % puissance;
					final Case c = grille.getCase(x, y);
					if (c.isSolved()) p |= c.getPossibilites();
				}
				for (int k = 0; k < length; k++) {
					final int x = a * puissance + k / puissance;
					final int y = b * puissance + k % puissance;
					final Case c = grille.getCase(x, y);
					if (!c.isSolved()) c.setPossibilites(~p & c.getPossibilites());
				}
			}
		}
		
		/**
		 * Test la suppression de la valeur. La grille doit toujours être résolvable au niveau level, mais pas au niveau level - 1.
		 * @return 1 si la grille est toujours résolvable au niveau level et level -1 (=> la valeur peut être supprimée)
		 * @return 0 si la grille est toujours résolvable au niveau level mais pas au level -1 (=> la valeur peut être supprimée, et on a fini)
		 * @return -1 sinon
		 */
		private int testerSupprimerValeur(int i, int j) {
			//test résolution level
			Grille testedGrille = clonerGrille();
			testedGrille.getCase(i, j).reset(puissance);
			try {
				boolean test = solver.solve(testedGrille, level); 
				if (!test) return -1;
			} catch (SolverException se) {
				return -1;
			}
			//test résolution level - 1
			testedGrille = clonerGrille();
			testedGrille.getCase(i, j).reset(puissance);
			try {
				return solver.solve(testedGrille, level - 1) ? 1 : 0;
			} catch (SolverException se) {
				return 0;
			}
		}
		
		private void supprimerValeur(int i, int j) {
			grille.getCase(i, j).reset(puissance);
		}
		
		private List getValeursPossibles(long p) {
			final int[] valeurs = PossibilitesUtils.getValeursPossibles(p);
			final int valeursLength = valeurs.length;
			List res = new ArrayList();
			for (int i = 0; i < valeursLength; i++) res.add(new Integer(valeurs[i]));
			return res;
		}
		
		/**
		 * @return liste des cases pour lesquelles il est possible d'affecter une valeur parmi plusieurs
		 */
		private List getCasesPossibles() {
			final List res = new ArrayList();
			for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++) {
				final Case c = grille.getCase(i, j);
				if (c.isSolved() || (PossibilitesUtils.getNbPossibilites(c.getPossibilites()) == 1)) continue;
				res.add(new int[] { i, j});
			}
			return res;
		}
		
		/**
		 * @return la liste des cases ayant une valeur
		 */
		private List getCasesValorisees() {
			final List res = new ArrayList();
			for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++) {
				final Case c = grille.getCase(i, j);
				if (c.isSolved()) res.add(new int[] { i, j});
			}
			return res;
		}
		
		/**
		 * Clone la grille et renvoie le clone.
		 * Les possibilités des cases non résolues sont resetés 
		 * @return
		 */
		private Grille clonerGrille() {
			Grille g = (Grille) grille.clone();
			for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++) {
				Case c = g.getCase(i, j);
				if (!c.isSolved()) c.reset(puissance);
			}
			return g;
		}
	}
}
