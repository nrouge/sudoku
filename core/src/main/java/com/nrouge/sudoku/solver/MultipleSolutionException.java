package com.nrouge.sudoku.solver;

/**
 * 
 * @author Nicolas Rougé
 */
public final class MultipleSolutionException extends SolverException {

	public MultipleSolutionException() {
		super("Pas de solution unique");
	}

}
