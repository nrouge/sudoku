package com.nrouge.sudoku.generator;

/**
 * 
 * @author Nicolas Rougé
 */
public interface IGeneratorProgressionListener {

	/**
	 * Méthode appelée lorsqu'une valeur a été placée sur une case (1ère partie de l'algorithme)
	 */
	void caseCreated();
	
	/**
	 * Méthode appelée lorsque la valeur d'une case a été supprimée (2ème partie de l'algorithme)
	 */
	void caseDeleted();
	
	/**
	 * Méthode appelée lorsque le générateur se réinitialise suite à une tentative de génération infructueuse 
	 */
	void reset();
}
