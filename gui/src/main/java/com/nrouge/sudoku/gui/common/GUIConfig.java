package com.nrouge.sudoku.gui.common;

import java.io.Serializable;

import com.nrouge.sudoku.model.Grille;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Nicolas Rougé
 */
@Getter
@Setter
public class GUIConfig implements Serializable {

	/**
	 * La grille
	 */
	private Grille grille;

	/**
	 * Indique s'il faut montrer les possibilités
	 */
	private boolean showPossibilites;
	
	/**
	 * Puissance du sudoku
	 */
	private byte puissance;
	
	/**
	 * Niveau de résolution
	 */
	private int resolutionLevel = 0;
	
	/**
	 * Niveau de génération
	 */
	private int generationLevel = 0;
	
	public GUIConfig(byte puissance) {
		this.puissance = puissance;
		reset(true);
	}
	
	public void reset() {
		reset(true);
	}
	
	public void reset(Grille grille) {
		this.grille = grille;
		puissance = grille.getPuissance();
		reset(false);
	}
	
	private void reset(boolean creerGrille) {
		if (creerGrille) grille = new Grille(puissance);
		showPossibilites = false;
	}

}
