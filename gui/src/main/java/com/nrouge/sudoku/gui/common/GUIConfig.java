package com.nrouge.sudoku.gui.common;

import java.io.Serializable;

import com.nrouge.sudoku.model.Grille;

/**
 * 
 * @author Nicolas Rougé
 */
public class GUIConfig implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7398356762960339850L;

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

	/**
	 * Returns the grille
	 * @return Grille
	 */
	public Grille getGrille() {
		return grille;
	}

	/**
	 * Returns the puissance
	 * @return byte
	 */
	public byte getPuissance() {
		return puissance;
	}

	/**
	 * Sets the puissance
	 * @param puissance The puissance to set.
	 */
	public void setPuissance(byte puissance) {
		this.puissance = puissance;
	}

	/**
	 * Returns the showPossibilites
	 * @return boolean
	 */
	public boolean isShowPossibilites() {
		return showPossibilites;
	}

	/**
	 * Sets the showPossibilites
	 * @param showPossibilites The showPossibilites to set.
	 */
	public void setShowPossibilites(boolean showPossibilites) {
		this.showPossibilites = showPossibilites;
	}

	/**
	 * Returns the generationLevel
	 * @return int
	 */
	public int getGenerationLevel() {
		return generationLevel;
	}

	/**
	 * Sets the generationLevel
	 * @param generationLevel The generationLevel to set.
	 */
	public void setGenerationLevel(int generationLevel) {
		this.generationLevel = generationLevel;
	}

	/**
	 * Returns the resolutionLevel
	 * @return int
	 */
	public int getResolutionLevel() {
		return resolutionLevel;
	}

	/**
	 * Sets the resolutionLevel
	 * @param resolutionLevel The resolutionLevel to set.
	 */
	public void setResolutionLevel(int resolutionLevel) {
		this.resolutionLevel = resolutionLevel;
	}
}
