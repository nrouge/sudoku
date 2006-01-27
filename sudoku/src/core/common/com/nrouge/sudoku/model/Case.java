package com.nrouge.sudoku.model;

import com.nrouge.sudoku.util.PossibilitesUtils;

public final class Case implements Cloneable {
	
	/**
	 * Masque des possibilités pour la case
	 */
	private long possibilites;
	
	/**
	 * Identifiant de la case (utilisé pour les logs)
	 */
	private final String id;
	
	/**
	 * Indique si la case est résolue
	 */
	private boolean solved;
	
	/**
	 * Constructeur
	 * @param puissance puissance du sudoku correspondant
	 * @param id identifiant de la case
	 */
	public Case(byte puissance, String id) {
		this.id = id;
		possibilites = (1 << (puissance * puissance)) - 1;
	}
	
	// getters

	/**
	 * @return Returns the possibilites.
	 */
	public long getPossibilites() {
		return possibilites;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return booléen indiquant si la case est résolue
	 */
	public boolean isSolved() {
		return solved;
	}
	
	/**
	 * @return la valeur de la case, -1 si elle n'a aucune valeur encore déterminée
	 */
	public int getValeur() {
		return solved ? PossibilitesUtils.getValeur(possibilites) : -1;
	}
	
	// setters
	
	/**
	 * Renseigne les possibilités pour la case
	 * @param possibilites The possibilites to set.
	 */
	public void setPossibilites(long possibilites) {
		this.possibilites = possibilites;
	}
	
	//autres

	/**
	 * Renseigne la valeur de la case
	 * @param valeur la valeur
	 */
	public void setValeur(int valeur) {
		if (valeur == -1) return;
		possibilites = 1 << valeur;
		solved = true;
	}
	
	public void reset(byte puissance) {
		possibilites = (1 << (puissance * puissance)) - 1;
		solved = false;
	}
	
	/**
	 * Teste si l'ensemble des possibilités ne se résume qu'à une seule valeur, et renseigne la propriété solved
	 * en conséquence. A appeler en général après un setPossibilites.
	 * @return solved
	 */
	public boolean calculerIsSolved() {
		solved = (PossibilitesUtils.getValeur(possibilites) != -1);
		return solved;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuffer(id).append(':').append(solved).append(':').append(Long.toBinaryString(possibilites)).toString();
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
	}
}
