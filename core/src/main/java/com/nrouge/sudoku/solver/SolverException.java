package com.nrouge.sudoku.solver;

/**
 * Exception déclenchée par le solver lorsqu'une erreur est
 * rencontrée (sudoku incorrect)
 * 
 * @author Nicolas Rougé
 */
public abstract class SolverException extends Exception {

	/**
	 * Constructeur
	 * @param message message de l'exception
	 */
	SolverException(String message) {
		super(message);
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return this;
	}
}
