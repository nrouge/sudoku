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
	Grille generate(byte puissance, int level);
	
	Grille generate(byte puissance, int level, IGeneratorProgressionListener gpl);
}
