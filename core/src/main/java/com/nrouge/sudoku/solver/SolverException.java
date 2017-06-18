package com.nrouge.sudoku.solver;

/**
 * Exception déclenchée par le solver lorsqu'une erreur est
 * rencontrée (sudoku incorrect)
 * 
 * @author Nicolas Rougé
 */
public abstract class SolverException extends Exception {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8042373426724424348L;

	/**
	 * Constructeur
	 * @param message message de l'exception
	 */
	SolverException(String message) {
		super(message);
	}

}
