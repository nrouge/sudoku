package com.nrouge.sudoku.solver;

import com.nrouge.sudoku.model.Case;

/**
 * 
 * @author Nicolas Rougé
 */
public final class UnsolvableCaseException extends SolverException {

	public UnsolvableCaseException(Case c) {
		super("La case "+c.getId()+" se retrouve sans possibilités");
	}

}
