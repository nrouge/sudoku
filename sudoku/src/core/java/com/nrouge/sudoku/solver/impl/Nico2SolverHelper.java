package com.nrouge.sudoku.solver.impl;

import com.nrouge.sudoku.model.Case;
import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.ICaseChangeListener;
import com.nrouge.sudoku.solver.ISolver;
import com.nrouge.sudoku.solver.MultipleSolutionException;
import com.nrouge.sudoku.solver.UndeterminedSolutionException;
import com.nrouge.sudoku.solver.UnsolvableCaseException;
import com.nrouge.sudoku.util.PossibilitesUtils;

/**
 * 
 * @author Nicolas Rougé
 */
class Nico2SolverHelper extends NicoSolverHelper {
	
	//private static final Log log = LogFactory.getLog(Nico2SolverHelper.class);

	/**
	 * @param g
	 */
	Nico2SolverHelper(Grille g, ICaseChangeListener ccl) {
		super(g, ccl);
	}

	/**
	 * @see com.nrouge.sudoku.solver.impl.NicoSolverHelper#solve4(int, com.nrouge.sudoku.solver.ISolver)
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
	
	

}
