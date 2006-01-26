package com.nrouge.sudoku.solver;

/**
 * 
 * @author Nicolas Rougé
 */
public final class MultipleSolutionException extends SolverException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8702560076432573372L;

	public MultipleSolutionException() {
		super("Pas de solution unique");
	}

}
