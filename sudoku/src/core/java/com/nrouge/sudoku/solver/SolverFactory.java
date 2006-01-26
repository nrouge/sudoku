package com.nrouge.sudoku.solver;

import com.nrouge.sudoku.solver.impl.NicoSolver;


public final class SolverFactory {

	private SolverFactory() {
		super();
	}
	
	public static ISolver createSolver(String solverId) {
		if (SolverConstants.NICO_SOLVER.equals(solverId)) {
			return new NicoSolver();
		} else {
			return null;
		}
	}
	
	public static ISolver createDefaultSolver() {
		return createSolver(SolverConstants.DEFAULT_SOLVER);
	}

}
