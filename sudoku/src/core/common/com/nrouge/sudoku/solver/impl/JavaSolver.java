package com.nrouge.sudoku.solver.impl;

import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.ICaseChangeListener;
import com.nrouge.sudoku.solver.ISolver;
import com.nrouge.sudoku.solver.MultipleSolutionException;
import com.nrouge.sudoku.solver.UndeterminedSolutionException;
import com.nrouge.sudoku.solver.UnsolvableCaseException;

public class JavaSolver implements ISolver {
	
	/**
	 * @see com.nrouge.sudoku.solver.ISolver#getMaxLevel()
	 */
	public final int getMaxLevel() {
		return 10;
	}

	/**
	 * @see ISolver#solve(Grille, int)
	 */
	public final boolean solve(Grille grille, int level) throws UnsolvableCaseException, MultipleSolutionException, UndeterminedSolutionException {
		return solve(grille, level, null);
	}
	
	/**
	 * @see com.nrouge.sudoku.solver.ISolver#solve(com.nrouge.sudoku.model.Grille, int, com.nrouge.sudoku.solver.ICaseChangeListener)
	 */
	public boolean solve(Grille grille, int level, ICaseChangeListener ccl) throws UnsolvableCaseException, MultipleSolutionException, UndeterminedSolutionException {
		final int length = grille.getLength();
		final int totalCases = length * length;
		int alreadySolved = grille.getSolvedCount();
		int currentSolved = 0;
		boolean cont = true; //indique s'il faut continuer
		JavaSolverHelper helper = createHelper(grille, ccl);
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

	JavaSolverHelper createHelper(Grille grille, ICaseChangeListener ccl) {
		return new JavaSolverHelper(grille, ccl);
	}
}
