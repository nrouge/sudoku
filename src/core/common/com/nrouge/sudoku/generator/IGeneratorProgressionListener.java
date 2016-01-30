package com.nrouge.sudoku.generator;

/**
 * 
 * @author Nicolas Roug�
 */
public interface IGeneratorProgressionListener {

	/**
	 * M�thode appel�e lorsqu'une valeur a �t� plac�e sur une case (1�re partie de l'algorithme)
	 */
	void caseCreated();
	
	/**
	 * M�thode appel�e lorsque la valeur d'une case a �t� supprim�e (2�me partie de l'algorithme)
	 */
	void caseDeleted();
	
	/**
	 * M�thode appel�e lorsque le g�n�rateur se r�initialise suite � une tentative de g�n�ration infructueuse 
	 */
	void reset();
}
