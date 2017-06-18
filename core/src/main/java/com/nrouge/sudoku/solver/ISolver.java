package com.nrouge.sudoku.solver;

import com.nrouge.sudoku.model.Grille;

/**
 * Représente un solver de sudoku
 * @author Nicolas Rougé
 */
public interface ISolver {
	
	/**
	 * Résout le sudoku
	 * @param grille le sudoku à résoudre
	 * @param level niveau de résolution (à partir de 0)
	 * @return true si le sudoku a été résolu, false sinon
	 * @throws UnsolvableCaseException si une case se retrouve sans solution
	 * @throws MultipleSolutionException si le sudoku n'a pas de solution unique
	 * @throws UndeterminedSolutionException si le solver ne sait pas s'il y a une solution (pour les niveaux de résolution emettant des suppositions)
	 */
	default boolean solve(Grille grille, int level) throws UnsolvableCaseException, MultipleSolutionException, UndeterminedSolutionException {
		return solve(grille, level, null);
	}
	
	boolean solve(Grille grille, int level, ICaseChangeListener ccl) throws UnsolvableCaseException, MultipleSolutionException, UndeterminedSolutionException;
	
}
