package com.nrouge.sudoku.solver;

import com.nrouge.sudoku.model.Case;

/**
 * 
 * @author Nicolas Rougé
 */
public final class UnsolvableCaseException extends SolverException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7478358135020126498L;

	/**
	 * @param message
	 */
	public UnsolvableCaseException(Case c) {
		super("La case "+c.getId()+" se retrouve sans possibilités");
	}

}
