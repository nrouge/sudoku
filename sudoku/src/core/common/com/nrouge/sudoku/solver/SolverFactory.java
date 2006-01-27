package com.nrouge.sudoku.solver;

import com.nrouge.sudoku.solver.impl.JavaSolver;


public final class SolverFactory {

	private SolverFactory() {
		super();
	}
	
	public static ISolver createSolver(String solverId) {
		if (SolverConstants.NICO_SOLVER.equals(solverId)) {
			return new JavaSolver();
		} else {
			return null;
		}
	}
	
	public static ISolver createDefaultSolver() {
		return createSolver(SolverConstants.DEFAULT_SOLVER);
	}

}
