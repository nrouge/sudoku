package com.nrouge.sudoku.solver;

import com.nrouge.sudoku.model.Grille;

/**
 * Repr�sente un solver de sudoku
 * @author Nicolas Roug�
 */
public interface ISolver {
	
	/**
	 * R�sout le sudoku
	 * @param grille le sudoku � r�soudre
	 * @param level niveau de r�solution (� partir de 0)
	 * @return true si le sudoku a �t� r�solu, false sinon
	 * @throws UnsolvableCaseException si une case se retrouve sans solution
	 * @throws MultipleSolutionException si le sudoku n'a pas de solution unique
	 * @throws UndeterminedSolutionException si le solver ne sait pas s'il y a une solution (pour les niveaux de r�solution emettant des suppositions)
	 */
	default boolean solve(Grille grille, int level) throws UnsolvableCaseException, MultipleSolutionException, UndeterminedSolutionException {
		return solve(grille, level, null);
	}
	
	boolean solve(Grille grille, int level, ICaseChangeListener ccl) throws UnsolvableCaseException, MultipleSolutionException, UndeterminedSolutionException;
	
}
