package com.nrouge.sudoku.solver.impl;

import com.nrouge.sudoku.model.Grille;

public class Nico2Solver extends NicoSolver {

	/**
	 * @see com.nrouge.sudoku.solver.impl.NicoSolver#createHelper(com.nrouge.sudoku.model.Grille)
	 */
	NicoSolverHelper createHelper(Grille g) {
		return new Nico2SolverHelper(g);
	}


}
