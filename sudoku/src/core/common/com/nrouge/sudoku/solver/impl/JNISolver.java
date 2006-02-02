package com.nrouge.sudoku.solver.impl;

import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.ICaseChangeListener;
import com.nrouge.sudoku.solver.ISolver;
import com.nrouge.sudoku.solver.MultipleSolutionException;
import com.nrouge.sudoku.solver.UndeterminedSolutionException;
import com.nrouge.sudoku.solver.UnsolvableCaseException;

/**
 * 
 * @author Nicolas Rougé
 */
public class JNISolver implements ISolver {

	/**
	 * @see com.nrouge.sudoku.solver.ISolver#solve(com.nrouge.sudoku.model.Grille, int)
	 */
	public boolean solve(Grille grille, int level) throws UnsolvableCaseException, MultipleSolutionException, UndeterminedSolutionException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see com.nrouge.sudoku.solver.ISolver#solve(com.nrouge.sudoku.model.Grille, int, com.nrouge.sudoku.solver.ICaseChangeListener)
	 */
	public boolean solve(Grille grille, int level, ICaseChangeListener ccl) throws UnsolvableCaseException,
			MultipleSolutionException, UndeterminedSolutionException {
		// TODO Auto-generated method stub
		return false;
	}

}
