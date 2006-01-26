package com.nrouge.sudoku.gui.common;

import com.nrouge.sudoku.model.Grille;

/**
 * 
 * @author Nicolas Rougé
 */
public class GUIConfig {

	private boolean showPossibilites = false;
	private boolean stopped = false;
	private Grille grille;
	private byte puissance;
	
	/**
	 * Returns the grille
	 * @return Grille
	 */
	public Grille getGrille() {
		return grille;
	}

	/**
	 * Sets the grille
	 * @param grille The grille to set.
	 */
	public void setGrille(Grille grille) {
		this.grille = grille;
		puissance = grille.getPuissance();
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
		grille = new Grille(puissance);
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
	 * Returns the stopped
	 * @return boolean
	 */
	public boolean isStopped() {
		return stopped;
	}

	/**
	 * Sets the stopped
	 * @param stopped The stopped to set.
	 */
	public void setStopped(boolean stopped) {
		this.stopped = stopped;
	}

	public GUIConfig(byte puissance) {
		this(new Grille(puissance));
	}

	/**
	 * 
	 */
	public GUIConfig(Grille grille) {
		this.grille = grille;
	}

}
