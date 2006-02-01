package com.nrouge.sudoku.generator.impl;

import com.nrouge.sudoku.generator.IGenerator;
import com.nrouge.sudoku.generator.IGeneratorProgressionListener;
import com.nrouge.sudoku.model.Case;
import com.nrouge.sudoku.model.Grille;
import com.nrouge.sudoku.solver.ISolver;
import com.nrouge.sudoku.solver.SolverFactory;

/**
 * 
 * @author Nicolas Roug�
 */
public final class JavaGenerator implements IGenerator {
	
	/**
	 * @see com.nrouge.sudoku.generator.IGenerator#generate(byte, int)
	 */
	public Grille generate(byte puissance, int level) {
		return generate(puissance, level, null);
	}

	/**
	 * @see com.nrouge.sudoku.generator.IGenerator#generate(byte, int, com.nrouge.sudoku.generator.IGeneratorProgressionListener)
	 */
	public Grille generate(byte puissance, int level, IGeneratorProgressionListener gpl) {
		Grille res = null;
		ISolver solver = SolverFactory.createDefaultSolver();
		do {
			res = new JavaGeneratorHelper(puissance, solver, level, gpl).generate();
		} while (res == null);
		final int length = res.getLength();
		for (int i = 0; i < length; i++)
		for (int j = 0; j < length; j++) {
			Case c = res.getCase(i, j);
			if (!c.isSolved()) c.reset(puissance);
		}
		return res;
	}
}
