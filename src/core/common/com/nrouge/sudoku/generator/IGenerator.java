package com.nrouge.sudoku.generator;

import com.nrouge.sudoku.model.Grille;

/**
 * 
 * @author Nicolas Roug�
 */
public interface IGenerator {

	
	
	/**
	 * G�n�re un sudoku en utilisant le solver par d�faut
	 * @param puissance
	 * @param level niveau 
	 * @return le sudoku
	 */
	Grille generate(byte puissance, int level);
	
	Grille generate(byte puissance, int level, IGeneratorProgressionListener gpl);
}
