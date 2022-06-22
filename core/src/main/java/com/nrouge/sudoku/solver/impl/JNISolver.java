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

	@Override
	public boolean solve(Grille grille, int level, ICaseChangeListener ccl) throws UnsolvableCaseException,
			MultipleSolutionException, UndeterminedSolutionException {
		// TODO Auto-generated method stub
		return false;
	}

}
