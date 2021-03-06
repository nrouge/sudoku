package com.nrouge.sudoku.generator;

import com.nrouge.sudoku.model.Grille;

/**
 * 
 * @author Nicolas Rougé
 */
public interface IGenerator {

	
	
	/**
	 * Génère un sudoku en utilisant le solver par défaut
	 * @param puissance
	 * @param level niveau 
	 * @return le sudoku
	 */
	default Grille generate(byte puissance, int level) {
		return generate(puissance, level, null);
	}
	
	Grille generate(byte puissance, int level, IGeneratorProgressionListener gpl);
}
