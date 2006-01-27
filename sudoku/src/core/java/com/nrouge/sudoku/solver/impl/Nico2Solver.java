package com.nrouge.sudoku.solver.impl;

import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.ICaseChangeListener;

public class Nico2Solver extends NicoSolver {

	/**
	 * @see com.nrouge.sudoku.solver.impl.NicoSolver#createHelper(com.nrouge.sudoku.model.Grille)
	 */
	NicoSolverHelper createHelper(Grille grille, ICaseChangeListener ccl) {
		return new Nico2SolverHelper(grille, ccl);
	}


}
