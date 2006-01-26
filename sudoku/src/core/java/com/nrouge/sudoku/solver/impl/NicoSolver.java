package com.nrouge.sudoku.solver.impl;

import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.ISolver;
import com.nrouge.sudoku.solver.MultipleSolutionException;
import com.nrouge.sudoku.solver.UndeterminedSolutionException;
import com.nrouge.sudoku.solver.UnsolvableCaseException;

public class NicoSolver implements ISolver {
	
	/**
	 * @see com.nrouge.sudoku.solver.ISolver#getMaxLevel()
	 */
	public final int getMaxLevel() {
		return 10;
	}

	/**
	 * @see ISolver#solve(Grille, int)
	 */
	public final boolean solve(Grille g, int level) throws UnsolvableCaseException, MultipleSolutionException, UndeterminedSolutionException {
		final int length = g.getLength();
		final int totalCases = length * length;
		int alreadySolved = g.getSolvedCount();
		int currentSolved = 0;
		boolean cont = true; //indique s'il faut continuer
		NicoSolverHelper helper = createHelper(g);
		while (cont && ((alreadySolved + currentSolved) < totalCases)) {
			//niveau 0
			cont = helper.solve0();
			if (cont) currentSolved++;
			if (cont || (level == 0)) continue;
			//niveau 1
			cont = helper.solve1();
			if (cont) currentSolved++;
			if (cont || (level == 1)) continue;
			//niveau 2
			int test = helper.solve2();
			if (test > 0) currentSolved += test;
			cont = (test != -1);
			if (cont || (level == 2)) continue;
			//niveau 3
			test = helper.solve3();
			if (test > 0) currentSolved += test;
			cont = (test != -1);
			if (cont || (level == 3)) continue;
			//niveau 4 et supérieurs
			cont = helper.solve4(level, this);
			if (cont) currentSolved++;
		}
		return (alreadySolved + currentSolved) == totalCases;
	}
	
	NicoSolverHelper createHelper(Grille g) {
		return new NicoSolverHelper(g);
	}
}
