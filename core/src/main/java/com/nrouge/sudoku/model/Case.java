package com.nrouge.sudoku.model;

import java.io.Serializable;

import com.nrouge.sudoku.util.PossibilitesUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
public final class Case implements Cloneable, Serializable {

	/**
	 * Masque des possibilités pour la case
	 */
	@Setter
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
	 * @return la valeur de la case, -1 si elle n'a aucune valeur encore déterminée
	 */
	public int getValeur() {
		return solved ? PossibilitesUtils.getValeur(possibilites) : -1;
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

	@Override
	public String toString() {
		return id + ":" + solved + ":" + Long.toBinaryString(possibilites);
	}

	@Override
	protected Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
	}
}
