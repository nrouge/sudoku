package com.nrouge.sudoku.solver.impl;

import com.nrouge.sudoku.model.Grille;

/**
 * 
 * @author Nicolas Rougé
 */
class JNISolverHelper {
	
	native void solve(Grille grille, int level);

}
